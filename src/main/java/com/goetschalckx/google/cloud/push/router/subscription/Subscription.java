package com.goetschalckx.google.cloud.push.router.subscription;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID subscriptionId;

    private String topic;
    public String name;
    public String url;
    public String description;
    private String headerJson;
    private String type;

    /*public OffsetDateTime createdOn;
    public OffsetDateTime updatedOn;
    public String createdBy;
    public String updatedBy;*/

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<AttributeFilter> attributeFilters = new HashSet<>();

    public Subscription() {
        this.subscriptionId = UUID.randomUUID();
    }

    public Subscription(
            UUID subscriptionId,
            String topic,
            String url,
            String type,
            String headerJson,
            Set<AttributeFilter> attributeFilters
    ) {
        this.subscriptionId = subscriptionId;
        this.topic = topic;
        this.url = url;
        this.type = type;
        this.headerJson = headerJson;
        this.attributeFilters.addAll(attributeFilters);
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public String getType() {
        return type;
    }

    public void setSubscriptionId(UUID subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    public void setType(String type) {
        this.type = type;
    }

}
