package com.goetschalckx.google.cloud.push.router.publish;

import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class PublishController {

    private final PubSubOperations pubSubOperations;

    public PublishController(
            PubSubOperations pubSubOperations
    ) {
        this.pubSubOperations = pubSubOperations;
    }

    @RequestMapping("publish")
    public void publish() throws ExecutionException, InterruptedException {
        pubSubOperations.publish("cloud-push", "foobar").get();
    }

}
