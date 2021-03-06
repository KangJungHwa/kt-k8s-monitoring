package com.kt.monitoring.repository;

import com.kt.monitoring.model.entity.NodeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface NodeRepository extends CrudRepository<NodeEntity, Long> {
    Optional<NodeEntity> findByNodename(String desc);

    @Transactional
    @Modifying
    @Query(value= "delete FROM aicc_nlu_framework.monitoring_node_resource_usage where create_ts < date_add(now(), interval -30 minute)",
            nativeQuery = true)
    void deleteNodeTableNative();
}
