package com.kt.monitoring.model.entity.pods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kt.monitoring.model.entity.nodes.Labels;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {
    @JsonProperty("name")
    String name;

    @JsonProperty("namespace")
    String nameSpace;

    @JsonProperty("labels")
    Labels labels;
}
