package com.goetschalckx.google.cloud.push.router.subscription.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID subscriptionId;

    private String topic;

    private String name;

    private String url;

    private String desc;

    private String enterpriseUnitId;

    private String headerJson;

    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "subscriptionId", referencedColumnName = "attributeId")
    })*/
    //private Set<AttributeFilter> attrs = new HashSet<>();

    private String type;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdOn;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedOn;

    private String createdBy;

    private String updatedBy;

}
