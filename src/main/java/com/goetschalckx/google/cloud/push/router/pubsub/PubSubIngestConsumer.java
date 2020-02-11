package com.goetschalckx.google.cloud.push.router.pubsub;

import com.goetschalckx.google.cloud.push.router.fanout.FanOutService;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class PubSubIngestConsumer {

    private final FanOutService fanOutService;
    private final PubSubHttpProducer pubSubHttpProducer;
    private final RetryTemplate retryTemplate;

    @ServiceActivator(inputChannel = "ingestMessageChannel")
    public void receive(Message<String> message) {
        log.info("Got message on ingest topic: {}\nHeaders {}", message.getPayload(), message.getHeaders());

        List<CloudPushMessage> messagesToPublish = fanOutService.fanOut(message);

        if (messagesToPublish != null && !messagesToPublish.isEmpty()) {
            messagesToPublish
                    .parallelStream()
                    .forEach(pubSubPayload ->
                            retryTemplate.execute((RetryCallback<Void, RuntimeException>) context -> {
                                pubSubHttpProducer.publish(pubSubPayload);
                                return null;
                            }, e -> {
                                log.error("Exhausted retries attempting to publish message {} {}", pubSubPayload, e);
                                return null;
                            }));
        } else {
            log.info("No subscribers matching message {}", message);
        }
    }

}
