package com.goetschalckx.google.cloud.push.router.pubsub;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "cloud-push")
public interface PubSubMessageGateway {

    void sendToPubsub(String text);

}
