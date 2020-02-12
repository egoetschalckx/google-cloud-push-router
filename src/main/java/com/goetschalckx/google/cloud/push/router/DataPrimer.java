package com.goetschalckx.google.cloud.push.router;

import com.goetschalckx.google.cloud.push.router.pubsub.PubSubMessageGateway;
import com.goetschalckx.google.cloud.push.router.subscription.data.Subscription;
import com.goetschalckx.google.cloud.push.router.subscription.data.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DataPrimer {

    private static final String COMMA_DELIMITER = ",";

    @Autowired
    public DataPrimer(
            SubscriptionRepository subscriptionRepository,
            PubSubMessageGateway pubSubMessageGateway
    ) {
        pubSubMessageGateway.sendToPubsub("foobar");
        pubSubMessageGateway.sendToPubsub("foobar");
        pubSubMessageGateway.sendToPubsub("foobar");
        pubSubMessageGateway.sendToPubsub("foobar");
        pubSubMessageGateway.sendToPubsub("foobar");

        /*
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("subs.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));

                Subscription sub = new Subscription();

                UUID id = UUID.fromString(values[0]);

                sub.subscriptionId = id;

                subscriptionRepository.save(sub);
            }

            int temp = 42;
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
