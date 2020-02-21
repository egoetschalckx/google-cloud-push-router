package com.goetschalckx.google.cloud.push.router.pubsub;

import com.goetschalckx.google.cloud.push.router.pubsub.config.PubSubProperties;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import com.goetschalckx.google.cloud.push.router.pubsub.data.PubSubPublishingFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PubSubHttpProducer {

    final static Logger log = LoggerFactory.getLogger(PubSubHttpProducer.class);

    private final PubSubOperations pubSubOperations;
    private final PubSubProperties pubSubProperties;

    @Autowired
    public PubSubHttpProducer(PubSubOperations pubSubOperations, PubSubProperties pubSubProperties) {
        this.pubSubOperations = pubSubOperations;
        this.pubSubProperties = pubSubProperties;
    }

    public void publish(CloudPushMessage cloudPushMessage) {
        if (cloudPushMessage != null) {
            log.debug(
                    "Publishing message on HTTP topic: {}\nAttributes: {}",
                    pubSubProperties.httpMessageTopic,
                    cloudPushMessage.getHeaders());

            try {
                pubSubOperations
                        .publish(
                                pubSubProperties.httpMessageTopic,
                                cloudPushMessage.getMessage(),
                                cloudPushMessage.getHeaders())
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Unhandled exception during publish() to HTTP topic", e);
                throw new PubSubPublishingFailedException(e);
            }
        }
    }

}
