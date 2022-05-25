package com.kt.monitoring.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "monitoring_disk_usage")
@IdClass(DiskEntityPK.class)
public class DiskEntity implements Serializable {

    @Id
    @Column(name = "node_name", columnDefinition = "VARCHAR(100)", nullable = true)
    private String nodename;
    /**
     * @CreationTimestamp를 키로 사용할 수 없다.
     * 생성일 (이 필드에는 값을 입력하지 않아도 Hibernate가 INSERT시 자동으로 기록)
     */
    @Id
    @Column(name = "create_ts")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createDate;

    @Column(name = "file_system")
    String fileSystem;

    @Column(name = "total_bytes")
    Long totalBytes;

    @Column(name = "used_bytes")
    Long usedBytes;

    @Column(name = "available_bytes")
    Long availableBytes;

    @Column(name = "used_percent")
    String usedPercent;
}
