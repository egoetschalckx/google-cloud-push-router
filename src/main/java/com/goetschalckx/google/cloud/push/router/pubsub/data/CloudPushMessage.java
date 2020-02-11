package com.goetschalckx.google.cloud.push.router.pubsub.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;

@Data
@AllArgsConstructor
public class CloudPushMessage {

    private String message;
    private Map<String, String> headers;

}
