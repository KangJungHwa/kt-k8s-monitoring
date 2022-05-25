package com.kt.monitoring.schedule;

import com.kt.monitoring.model.entity.DiskEntity;
import com.kt.monitoring.repository.DiskRepository;
import com.kt.monitoring.util.EncryptionUtils;
import com.kt.monitoring.util.SSHUtils;
import com.kt.monitoring.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@ConfigurationProperties(prefix = "k8s")
public class DiskUsageTask {

    @Autowired
    DiskRepository deskRepository;
    private Map<String, String> nodes;
    private Map<String, Integer> ports;

    private String username;
    private String password;

    public void setUsername(final String username) {
        this.username = username;
    }
    public void setPassword(final String password) {
        this.password = password;
    }

    public void setNodes(final Map<String, String> nodes) {
        this.nodes = nodes;
    }
    public void setPorts(final Map<String, Integer> ports) {
        this.ports = ports;
    }

    @Scheduled(cron="0 * * * * *")
    public void run() throws Exception {
        Timestamp createTimestamp= TimeUtil.getNow();
        for (Object nodename:nodes.keySet()) {
           String responseStr=  SSHUtils.getSshResult(username,
                    EncryptionUtils.getDecodingStr(password),
                    nodes.get(nodename),
                    "df -t ext4 | grep -vE '^Filesystem|tmpfs|overlay|192.|shm' | awk '{ print $1 \" \" $2 \" \" $3 \" \" $4 \" \" $5}'",
                     ports.get(nodename));
            saveResult(nodename.toString(), responseStr,createTimestamp);

            if(nodename.toString().equals("nlu-framework-master-1")){
                String responseStr2=  SSHUtils.getSshResult(username,
                        EncryptionUtils.getDecodingStr(password),
                        nodes.get(nodename),
                        "df -P /nfs_mount | grep -vE '^Filesystem' | awk '{ print $1 \" \" $2 \" \" $3 \" \" $4 \" \" $5}'",
                        ports.get(nodename));
                    saveResult(nodename.toString(), responseStr2,createTimestamp);
            }
        }
    }

    /**
     * 명령어 실행결과 저장
     * @param paramNodename 노드명
     * @param responseStr 명령어 실행결과
     */
    public void saveResult(String paramNodename, String responseStr, Timestamp createTimestamp){
        String[] result = responseStr.split(" ");
        boolean isCheckStart=false;
        String fileSystem=null;
        Long totalBytes=0L;
        Long usedBytes=0L;
        Long availableBytes=0L;
        String usedPercent=null;

        List<DiskEntity> diskList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {

            if(result[i].length() >0 ) {
                switch (i){
                    case 0:
                        fileSystem=result[i].trim();
                        break;
                    case 1:
                        totalBytes=Long.parseLong(result[i].trim());
                        break;
                    case 2:
                        usedBytes=Long.parseLong(result[i].trim());
                        break;
                    case 3:
                        availableBytes=Long.parseLong(result[i].trim());
                        break;
                    case 4:
                        usedPercent=result[i].trim();
                        break;
                }
            }
        }
        String nodename=null;
        if(fileSystem.startsWith("192")){
            nodename=fileSystem;
        }else{
            nodename=paramNodename;
        }
        DiskEntity diskEntity = DiskEntity.builder()
                .createDate(createTimestamp)
                .nodename(nodename)
                .fileSystem(fileSystem)
                .totalBytes(totalBytes)
                .usedBytes(usedBytes)
                .availableBytes(availableBytes)
                .usedPercent(usedPercent).build();
        diskList.add(diskEntity);
        deskRepository.saveAll(diskList);
    }
}
