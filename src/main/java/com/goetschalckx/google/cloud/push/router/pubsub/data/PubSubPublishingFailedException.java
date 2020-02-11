package com.goetschalckx.google.cloud.push.router.pubsub.data;

public class PubSubPublishingFailedException extends RuntimeException {

    public PubSubPublishingFailedException(Exception e) {
        super(e);
    }

}
