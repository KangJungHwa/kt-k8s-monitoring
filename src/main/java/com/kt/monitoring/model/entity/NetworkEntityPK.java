package com.kt.monitoring.model.entity;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NetworkEntityPK implements Serializable {
    private String nodename;
    private Timestamp createDate;
}
