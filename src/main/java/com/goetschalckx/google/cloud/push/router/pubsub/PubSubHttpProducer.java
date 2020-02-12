package com.goetschalckx.google.cloud.push.router.pubsub;

import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import com.goetschalckx.google.cloud.push.router.pubsub.data.PubSubPublishingFailedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;

import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
public class PubSubHttpProducer {

    private PubSubOperations pubSubOperations;
    private PubSubProperties pubSubProperties;

    public void publish(CloudPushMessage cloudPushMessage) {

        if (cloudPushMessage != null) {
            log.info(
                    "Publishing message on HTTP topic: {}\nAttributes: {}",
                    cloudPushMessage.getMessage(),
                    cloudPushMessage.getHeaders());

            try {
                pubSubOperations
                        .publish(
                                pubSubProperties.getHttpMessageTopic(),
                                cloudPushMessage.getMessage(),
                                cloudPushMessage.getHeaders())
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception when publishing to HTTP queue {}", cloudPushMessage, e);
                throw new PubSubPublishingFailedException(e);
            }
        }

    }

}
