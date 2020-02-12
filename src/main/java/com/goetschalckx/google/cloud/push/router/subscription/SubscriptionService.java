package com.goetschalckx.google.cloud.push.router.subscription;

import com.goetschalckx.google.cloud.push.router.subscription.data.Subscription;
import com.goetschalckx.google.cloud.push.router.subscription.data.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubscriptionService {

    private SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getSubscriptions(
            String topicId,
            Map<String, String> attributes
    ) {
        return subscriptionRepository.findAll();
    }

}
