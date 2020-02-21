package com.goetschalckx.google.cloud.push.router.fanout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goetschalckx.google.cloud.push.router.config.MapStringStringType;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CustomAttribute;
import com.goetschalckx.google.cloud.push.router.pubsub.data.ReservedAttributes;
import com.goetschalckx.google.cloud.push.router.subscription.Subscription;
import com.goetschalckx.google.cloud.push.router.subscription.SubscriptionService;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class FanOutService {

    final static Logger log = LoggerFactory.getLogger(FanOutService.class);

    private final ObjectMapper objectMapper;
    private final SubscriptionService subscriptionService;

    @Autowired
    public FanOutService(
            ObjectMapper objectMapper,
            SubscriptionService subscriptionService
    ) {
        this.objectMapper = objectMapper;
        this.subscriptionService = subscriptionService;
    }

    private static ImmutableMap<String, String> defaultHeaders() {
        ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();

        //mapBuilder.put(ReservedAttributes.CORRELATION_ID, correlationId);
        mapBuilder.put(ReservedAttributes.RETRY_COUNT, "0");

        return mapBuilder.build();
    }

    private static List<CustomAttribute> constructCustomAttributeList(
            Map<String, String> messageAttributes
    ) {
        List<CustomAttribute> attributeList = new ArrayList<>();

        messageAttributes.forEach(
            (key, value) -> {
                CustomAttribute element = new CustomAttribute(key, value);
                attributeList.add(element);
            }
        );

        return attributeList;
    }

    public List<CloudPushMessage> fanOut(Message<String> message) {
        Map<String, String> messageAttributes = new HashMap<>();
        message
                .getHeaders()
                .forEach((key, value) -> messageAttributes.put(key, value.toString()));

        String messageBody = message.getPayload();

        String correlationId = UUID.randomUUID().toString();

        String topicId = messageAttributes.remove(ReservedAttributes.TOPIC_ID);

        // remove google-added attributes
        messageAttributes.remove(ReservedAttributes.TIMESTAMP);
        messageAttributes.remove(ReservedAttributes.ID);

        // get matching subscriptions
        List<Subscription> subscriptions =
                subscriptionService.getMatchingSubscriptions(topicId, messageAttributes);

        List<CustomAttribute> customAttributeList =
                    constructCustomAttributeList(messageAttributes);

        List<CloudPushMessage> messages =
                    processMessage(
                            messageBody,
                            topicId,
                            subscriptions,
                            customAttributeList,
                            defaultHeaders());

        return messages;
    }

    private List<CloudPushMessage> processMessage(
            String messageBody,
            String topicId,
            List<Subscription> subscriptions,
            List<CustomAttribute> messageCustomAttributes,
            Map<String, String> sharedHeaders
    ) {
        List<CloudPushMessage> pubSubPayloads = new ArrayList<>(subscriptions.size());
        for (Subscription subscription : subscriptions) {

            Map<String, String> outboundHeaders = new HashMap<>(sharedHeaders);
            outboundHeaders.put("url", subscription.url);

            try {
                Map<String, String> customHeaders = objectMapper.readValue(
                                subscription.getHeaderJson(),
                                new MapStringStringType());

                customHeaders.forEach(outboundHeaders::putIfAbsent);

                HttpMessagePayload httpMessagePayload =
                        new HttpMessagePayload(topicId, messageCustomAttributes, messageBody);

                String payload = objectMapper.writeValueAsString(httpMessagePayload);

                CloudPushMessage messages = new CloudPushMessage(payload, outboundHeaders);

                pubSubPayloads.add(messages);
            } catch (IOException e) {
                String msg = "IOException during processMessage() for subscription: " + subscription;
                log.error(msg, e);
            }
        }

        return pubSubPayloads;
    }

}
