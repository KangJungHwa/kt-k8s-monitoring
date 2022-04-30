package com.kt.monitoring.model.nodes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nodes {
    @JsonProperty("kind")
    String kind;
    @JsonProperty("apiVersion")
    String apiVersion;
    @JsonProperty("items")
    List<Item> items;

}
