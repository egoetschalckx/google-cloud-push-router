package com.goetschalckx.google.cloud.push.router.fanout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goetschalckx.google.cloud.push.router.config.MapStringStringType;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CustomAttribute;
import com.goetschalckx.google.cloud.push.router.pubsub.data.ReservedAttributes;
import com.goetschalckx.google.cloud.push.router.subscription.SubscriptionService;
import com.goetschalckx.google.cloud.push.router.subscription.data.Subscription;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.io.IOException;
import java.util.*;

@Slf4j
@AllArgsConstructor
public class FanOutService {

    private ObjectMapper objectMapper;
    private SubscriptionService subscriptionService;

    private static ImmutableMap<String, String> defaultHeaders() {
        ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();

        //mapBuilder.put(ReservedAttributes.CORRELATION_ID, correlationId);
        mapBuilder.put(ReservedAttributes.RETRY_COUNT, "0");

        return mapBuilder.build();
    }

    private static ImmutableList<CustomAttribute> constructCustomAttributeList(
            Map<String, String> messageAttributes) {
        ImmutableList.Builder<CustomAttribute> listBuilder = ImmutableList.builder();

        messageAttributes.forEach((key, value) -> {
            CustomAttribute element = new CustomAttribute(key, value);

            listBuilder.add(element);
        });

        return listBuilder.build();
    }

    public List<CloudPushMessage> fanOut(Message message) {
        Map<String, String> messageAttributes = new HashMap<>();
        message
                .getHeaders()
                .forEach((key, value) -> messageAttributes.put(key, value.toString()));

        String messageBody = message
                .getPayload()
                .toString();

        String correlationId = UUID.randomUUID().toString();

        String topicId = messageAttributes.remove(ReservedAttributes.TOPIC_ID);

        // remove google-added attributes
        messageAttributes.remove(ReservedAttributes.TIMESTAMP);
        messageAttributes.remove(ReservedAttributes.ID);

        // get matching subscriptions
        List<Subscription> subscriptions =
                subscriptionService.getSubscriptions(topicId, messageAttributes);

        ImmutableList<CustomAttribute> customCustomAttributeDataList =
                    constructCustomAttributeList(messageAttributes);

        List<CloudPushMessage> messages =
                    processMessage(
                            messageBody,
                            topicId,
                            subscriptions,
                            customCustomAttributeDataList,
                            defaultHeaders());

        return messages;
    }

    private List<CloudPushMessage> processMessage(
            String messageBody,
            String topicId,
            List<Subscription> subscriptions,
            ImmutableList<CustomAttribute> messageCustomAttributes,
            ImmutableMap<String, String> sharedHeaders
    ) {
        List<CloudPushMessage> pubSubPayloads = new ArrayList<>(subscriptions.size());
        for (Subscription subscription : subscriptions) {

            Map<String, String> outboundHeaders = new HashMap<>(sharedHeaders);
            outboundHeaders.put("url", subscription.getUrl());

            try {
                Map<String, String> customHeaders =
                        objectMapper.readValue(
                                subscription.getHeaderJson(),
                                new MapStringStringType());

                customHeaders.forEach(outboundHeaders::putIfAbsent);

                HttpMessagePayload httpMessagePayload = new HttpMessagePayload(topicId, messageCustomAttributes, messageBody);

                String payload = objectMapper.writeValueAsString(httpMessagePayload);

                CloudPushMessage messages = new CloudPushMessage(payload, outboundHeaders);

                pubSubPayloads.add(messages);
            } catch (IOException e) {
                log.error("IOException during processing for subscription: {}\nError: {}", subscription, e);
            }
        }

        return pubSubPayloads;
    }

}
