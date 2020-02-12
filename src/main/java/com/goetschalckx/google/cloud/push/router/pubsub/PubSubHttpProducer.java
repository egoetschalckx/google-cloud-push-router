package com.goetschalckx.google.cloud.push.router.pubsub;

import com.goetschalckx.google.cloud.push.router.pubsub.config.PubSubProperties;
import com.goetschalckx.google.cloud.push.router.pubsub.data.CloudPushMessage;
import com.goetschalckx.google.cloud.push.router.pubsub.data.PubSubPublishingFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
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
            log.info(
                    "Publishing message on HTTP topic: {}\nAttributes: {}",
                    cloudPushMessage.message,
                    cloudPushMessage.headers);

            try {
                pubSubOperations
                        .publish(
                                pubSubProperties.httpMessageTopic,
                                cloudPushMessage.message,
                                cloudPushMessage.headers)
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception when publishing to HTTP queue {}", cloudPushMessage, e);
                throw new PubSubPublishingFailedException(e);
            }
        }

    }

}
