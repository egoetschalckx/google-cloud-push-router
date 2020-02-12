package com.goetschalckx.google.cloud.push.router.publish;

import com.github.javafaker.Faker;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    PubSubTemplate pubSubTemplate;
    Faker faker;

    public PublishController(PubSubTemplate pubSubTemplate, Faker faker) {
        this.pubSubTemplate = pubSubTemplate;
        this.faker = faker;
    }

    @RequestMapping("publish")
    public void publish() {
        pubSubTemplate.publish("cloud-push", faker.dune().character());
    }

}
