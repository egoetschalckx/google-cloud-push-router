package com.goetschalckx.google.cloud.push.router.subscription.data;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeFilter implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private UUID attributeId;

    private UUID subscriptionId;

    private String key;

    private String value;

}
