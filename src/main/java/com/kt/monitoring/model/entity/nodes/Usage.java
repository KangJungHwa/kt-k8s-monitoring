package com.kt.monitoring.model.entity.nodes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {
    String cpu;
    String memory;
}
