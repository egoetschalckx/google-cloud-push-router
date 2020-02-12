package com.goetschalckx.google.cloud.push.router;

import com.github.javafaker.Faker;
import com.goetschalckx.google.cloud.push.router.subscription.Subscription;
import com.goetschalckx.google.cloud.push.router.subscription.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DataPrimer {

    private static final String COMMA_DELIMITER = ",";

    @Autowired
    public DataPrimer(
            SubscriptionRepository subscriptionRepository,
            Faker faker
    ) {
        for (int i = 0; i < 250; i++) {
            String topic = faker.options().option(Topic.class).name();

            Subscription sub = new Subscription();
            sub.subscriptionId = UUID.randomUUID();
            sub.topic = topic;
            sub.type = "http";
            sub.headerJson = "{'x-foo':'bar'}";

            subscriptionRepository.save(sub);
        }
    }

}
