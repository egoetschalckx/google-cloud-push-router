package com.goetschalckx.google.cloud.push.router.subscription;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AttributeFilter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID attributeId;

    private UUID subscriptionId;
    private String key;
    private String value;

    public AttributeFilter() {
        this.attributeId = UUID.randomUUID();
    }

    public AttributeFilter(
            String key,
            String value
    ) {
        this.key = key;
        this.value = value;
    }

    public AttributeFilter(
            UUID attributeId,
            UUID subscriptionId,
            String key,
            String value
    ) {
        this.attributeId = attributeId;
        this.subscriptionId = subscriptionId;
        this.key = key;
        this.value = value;
    }

    public UUID getAttributeId() {
        return attributeId;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setAttributeId(UUID attributeId) {
        this.attributeId = attributeId;
    }

    public void setSubscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
