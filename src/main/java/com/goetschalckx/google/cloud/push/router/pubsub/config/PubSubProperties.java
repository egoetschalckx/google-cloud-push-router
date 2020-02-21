package com.goetschalckx.google.cloud.push.router.pubsub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloud.push")
public class PubSubProperties {

    public String httpMessageTopic = "cloud-push-http";

}
