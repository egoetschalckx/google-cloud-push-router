package com.goetschalckx.google.cloud.push.router.pubsub;

import com.goetschalckx.google.cloud.push.router.fanout.FanOutService;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PubSubIngestConsumer {

    final static Logger log = LoggerFactory.getLogger(PubSubIngestConsumer.class);

    private final FanOutService fanOutService;
    private final PubSubHttpProducer pubSubHttpProducer;
    private final RetryTemplate retryTemplate;

    @Autowired
    public PubSubIngestConsumer(
            FanOutService fanOutService,
            PubSubHttpProducer pubSubHttpProducer,
            RetryTemplate retryTemplate
    ) {
        this.fanOutService = fanOutService;
        this.pubSubHttpProducer = pubSubHttpProducer;
        this.retryTemplate = retryTemplate;
    }

    @ServiceActivator(inputChannel = "cloudPushChannel")
    public void receive(Message<String> message) {
        // ?? ynowork
        //log.info("Got message on ingest topic: {} \nHeaders {}", message.getPayload(), message.getHeaders());
        log.info("got message on cloudPushChannel..");

        List<CloudPushMessage> messagesToPublish = fanOutService.fanOut(message);

        if (messagesToPublish != null && !messagesToPublish.isEmpty()) {
            messagesToPublish
                    .parallelStream()
                    .forEach(pubSubPayload ->
                            retryTemplate.execute((RetryCallback<Void, RuntimeException>) context -> {
                                pubSubHttpProducer.publish(pubSubPayload);
                                return null;
                            }, e -> {
                                String msg = "Exhausted retries attempting to publish message " + message;
                                log.error(msg, e);
                                return null;
                            }));
        } else {
            log.info("No subscribers matching message {}", message);
        }
    }

}
