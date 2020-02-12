package com.goetschalckx.google.cloud.push.router.fanout;

import com.goetschalckx.google.cloud.push.router.pubsub.data.CustomAttribute;
import com.google.common.collect.ImmutableList;

public class HttpMessagePayload {

    private String topicId;
    private ImmutableList<CustomAttribute> messageCustomAttributes;
    private String payload;

    public HttpMessagePayload(String topicId, ImmutableList<CustomAttribute> messageCustomAttributes, String payload) {
        this.topicId = topicId;
        this.messageCustomAttributes = messageCustomAttributes;
        this.payload = payload;
    }

}
