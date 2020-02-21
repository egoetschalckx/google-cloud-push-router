package com.goetschalckx.google.cloud.push.router.pubsub.data;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class CloudPushMessage {

    private final String message;
    private final Map<String, String> headers = new HashMap<>();

    public CloudPushMessage(String message, Map<String, String> headers) {
        this.message = message;
        this.headers.putAll(headers);
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
