package com.goetschalckx.google.cloud.push.router.fanout;

import com.goetschalckx.google.cloud.push.router.pubsub.data.CustomAttribute;
import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpMessagePayload {

    private String topicId;
    private ImmutableList<CustomAttribute> messageCustomAttributes;
    private String payload;

}
