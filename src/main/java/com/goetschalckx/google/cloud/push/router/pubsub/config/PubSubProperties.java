package com.goetschalckx.google.cloud.push.router.pubsub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class PubSubProperties {

    public String httpMessageTopic = "cloud-push-http";

}
