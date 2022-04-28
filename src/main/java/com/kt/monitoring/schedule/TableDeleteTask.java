package com.kt.monitoring.schedule;

import com.kt.monitoring.repository.GpuRepository;
import com.kt.monitoring.repository.NetworkRepository;
import com.kt.monitoring.repository.NodeRepository;
import com.kt.monitoring.repository.PodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TableDeleteTask {

    @Autowired
    PodRepository podRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    NetworkRepository networkRepository;

    @Autowired
    GpuRepository gpuRepository;

    @Scheduled(cron="0 0/30 * * * *")
    public void deletePodTable()  throws Exception {
        podRepository.deletePodTableNative();
    }
    @Scheduled(cron="0 0/30 * * * *")
    public void deleteNodeTable()  throws Exception {
        nodeRepository.deleteNodeTableNative();
    }
    @Scheduled(cron="0 0/30 * * * *")
    public void deleteNetworkTable()  throws Exception {
        networkRepository.deleteNetworkTableNative();
    }
    @Scheduled(cron="0 0/30 * * * *")
    public void deleteGpuTable()  throws Exception {
        gpuRepository.deleteGpuTableNative();
        //gpuRepository.selectGpuTableNative();
    }
}
