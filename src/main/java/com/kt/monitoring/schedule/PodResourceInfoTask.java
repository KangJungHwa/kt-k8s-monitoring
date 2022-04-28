package com.kt.monitoring.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.monitoring.controller.RestTemplateController;
import com.kt.monitoring.model.entity.PodEntity;
import com.kt.monitoring.model.entity.pods.Pods;
import com.kt.monitoring.repository.PodRepository;
import com.kt.monitoring.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PodResourceInfoTask extends RestTemplateController {
    @Autowired
    @Qualifier("mapper")
    ObjectMapper mapper;

    @Autowired
    PodRepository podRepository;

    @Value("${app.backend.k8s-apis.url}")
    String k8sApisUrl;

    @Value("${app.backend.k8s.token}")
    String token;

    @Autowired
    @Qualifier("sslRestTemplate")
    RestTemplate restTemplate;

    //1분마다 실행 kube-apiserver 호출
    //http://172.30.1.81:30003/k8s-apis/metrics.k8s.io/v1beta1/pods

    @Scheduled(cron="0 * * * * *")
    public void run() throws JsonProcessingException {
        HttpEntity<String> entity = emptyGetRequestEntity(token);
        String url = k8sApisUrl+"/metrics.k8s.io/v1beta1/pods";
        ResponseEntity<String> responseEntity= restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        Pods pods = mapper.readValue(responseEntity.getBody(), Pods.class);
        List<PodEntity> podUsageList = new ArrayList<>();
        java.sql.Timestamp creaTimestamp = TimeUtil.getNow();
        for(int i=0; i<pods.getItems().size(); i++) {
            for(int j=0; j<pods.getItems().get(i).getContainers().size(); j++) {
                String cpu=pods.getItems().get(i).getContainers().get(j).getUsage().getCpu();
                String memory=pods.getItems().get(i).getContainers().get(j).getUsage().getMemory();
                String memoryUnit=null;
                String cpuUnit=null;
                if(cpu.indexOf("m")>0) {
                    cpu = cpu.replaceAll("m", "");
                    cpuUnit="mili";
                }
                if(cpu.indexOf("u")>0) {
                    cpu = cpu.replaceAll("u", "");
                    cpuUnit="micro";
                }
                if(cpu.indexOf("n")>0) {
                    cpu = cpu.replaceAll("n", "");
                    cpuUnit="nano";
                }

                if(memory.indexOf("Ki")>0) {
                    memory = memory.replaceAll("Ki", "");
                    memoryUnit = "Ki";
                }
                if(memory.indexOf("Mi")>0) {
                    memory = memory.replaceAll("Mi", "");
                    memoryUnit = "Mi";
                }
                if(memory.indexOf("Gi")>0) {
                    memory = memory.replaceAll("Gi", "");
                    memoryUnit = "Gi";
                }

                PodEntity podEntity=PodEntity.builder()
                       .podname(pods.getItems().get(i).getMetadata().getName())
                       .nameSpace(pods.getItems().get(i).getMetadata().getNameSpace())
                       .containerName(pods.getItems().get(i).getContainers().get(j).getContainerName())
                       .cpu(Long.valueOf(cpu))
                       .cpuUnit(cpuUnit)
                       .memory(Long.valueOf(memory))
                       .memoryUnit(memoryUnit)
                       .createDate(creaTimestamp).build();
                podUsageList.add(podEntity);
                log.info("time : " + creaTimestamp +" pod : "+pods.getItems().get(i).getMetadata().getName() +" con : " +pods.getItems().get(i).getContainers().get(j).getContainerName());
            }
        }
        podRepository.saveAll(podUsageList);
    }
}
