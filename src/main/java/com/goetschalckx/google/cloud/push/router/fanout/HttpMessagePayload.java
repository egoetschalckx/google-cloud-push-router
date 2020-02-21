package com.goetschalckx.google.cloud.push.router.fanout;

import com.goetschalckx.google.cloud.push.router.pubsub.data.CustomAttribute;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class HttpMessagePayload {

    private final String topicId;
    private final List<CustomAttribute> messageCustomAttributes = new ArrayList<>();
    private final String payload;

    public HttpMessagePayload(String topicId, List<CustomAttribute> messageCustomAttributes, String payload) {
        this.topicId = topicId;
        this.messageCustomAttributes.addAll(messageCustomAttributes);
        this.payload = payload;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getPayload() {
        return payload;
    }

    public List<CustomAttribute> getMessageCustomAttributes() {
        return messageCustomAttributes;
    }

}
