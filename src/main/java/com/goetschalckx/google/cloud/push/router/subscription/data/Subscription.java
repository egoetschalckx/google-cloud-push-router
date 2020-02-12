package com.goetschalckx.google.cloud.push.router.subscription.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
public class Subscription {

    private static final long serialVersionUID = 1L;

    @Id
    public UUID subscriptionId;

    public String topic;
    public String name;
    public String url;
    public String description;
    public String headerJson;
    public String type;

    public OffsetDateTime createdOn;
    public OffsetDateTime updatedOn;
    public String createdBy;
    public String updatedBy;

    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "subscriptionId", referencedColumnName = "attributeId")
    })*/
    //private Set<AttributeFilter> attrs = new HashSet<>();

}
