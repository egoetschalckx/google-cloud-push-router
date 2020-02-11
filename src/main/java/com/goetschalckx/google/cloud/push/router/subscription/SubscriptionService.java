package com.goetschalckx.google.cloud.push.router.subscription;

import com.goetschalckx.google.cloud.push.router.subscription.data.Subscription;
import com.goetschalckx.google.cloud.push.router.subscription.data.SubscriptionRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptions(
            String topicId,
            Map<String, String> attributes
    ) {
        return subscriptionRepository.findAll();
    }

}
