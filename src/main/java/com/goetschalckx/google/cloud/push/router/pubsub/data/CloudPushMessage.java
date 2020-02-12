package com.goetschalckx.google.cloud.push.router.pubsub.data;

import java.util.Map;

public class CloudPushMessage {

    public String message;
    public Map<String, String> headers;

    public CloudPushMessage(String message, Map<String, String> headers) {
        this.message = message;
        this.headers = headers;
    }

}
