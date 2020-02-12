package com.goetschalckx.google.cloud.push.router.pubsub.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomAttribute {

    private String key;

    private String value;

}
