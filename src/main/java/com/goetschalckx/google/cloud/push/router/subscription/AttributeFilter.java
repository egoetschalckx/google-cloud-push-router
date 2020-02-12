package com.goetschalckx.google.cloud.push.router.subscription;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AttributeFilter {

    public static final long serialVersionUID = 1L;

    @Id
    public UUID attributeId;

    public UUID subscriptionId;
    public String key;
    public String value;

}
