package com.kt.monitoring.repository;

import com.kt.monitoring.model.entity.DiskEntity;
import com.kt.monitoring.model.entity.GpuEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface DiskRepository extends CrudRepository<DiskEntity, Long> {
    Optional<GpuEntity> findByNodename(String desc);

    @Transactional
    @Modifying
    @Query(value= "delete from aicc_nlu_framework.monitoring_disk_usage where create_ts < date_add(now(), interval -30 minute)", nativeQuery = true)
        void deleteDiskTableNative();
}

