package com.goetschalckx.google.cloud.push.router.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(
            SubscriptionRepository subscriptionRepository
    ) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getMatchingSubscriptions(
            String topic,
            Map<String, String> messageAttributes
    ) {
        return subscriptionRepository.findAll();
    }

}
