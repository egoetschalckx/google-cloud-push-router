package com.goetschalckx.google.cloud.push.router;

import com.github.javafaker.Faker;
import com.goetschalckx.google.cloud.push.router.subscription.AttributeFilter;
import com.goetschalckx.google.cloud.push.router.subscription.Subscription;
import com.goetschalckx.google.cloud.push.router.subscription.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class DataPrimer {

    private static final String COMMA_DELIMITER = ",";

    private static final String HEADER_JSON =  "{\"x-foo\":\"bar\"}";

    @Autowired
    public DataPrimer(
            Faker faker,
            SubscriptionRepository subscriptionRepository
    ) {
        List<Subscription> existingSubs = subscriptionRepository.findAll();

        if (existingSubs.size() > 0) {
            return;
        }

        AttributeFilter euAttributeFilter = new AttributeFilter("enterpriseUnitId", "123abc");
        AttributeFilter newStatusAttributeFilter = new AttributeFilter("newStatus", "OrderPlaced");

        for (int i = 0; i < 10; i++) {
            String topic = faker.options().option(Topic.class).name();

            UUID subscriptionId = UUID.randomUUID();

            euAttributeFilter.setSubscriptionId(subscriptionId);
            newStatusAttributeFilter.setSubscriptionId(subscriptionId);

            Set<AttributeFilter> attributeFilters = new HashSet<>();
            attributeFilters.add(euAttributeFilter);
            attributeFilters.add(newStatusAttributeFilter);

            Subscription sub = new Subscription(
                    subscriptionId,
                    topic,
                    "https://example.com",
                    "http",
                    HEADER_JSON,
                    attributeFilters
            );

            subscriptionRepository.save(sub);
        }
    }

}
