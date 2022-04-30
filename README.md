# AI Platform Moniotoring Server for KT

KT AI Platform의 리소스 Monitoring Server

## Build

```
# mvn -Dmaven.test.skip clean package

```


mariaDB 설치 이후 timezone변경
```
SET time_zone = 'Asia/Seoul';
SHOW GLOBAL VARIABLES LIKE 'time_zone';

SET GLOBAL time_zone = 'Asia/Seoul';
SHOW SESSION VARIABLES LIKE 'time_zone';
```

타입존 확인
```$xslt
select @@global.time_zone, @@session.time_zone
```


command line 실행 명령
```$xslt
nohup java -jar 'kt-k8s-monitoring-1.0.0-SNAPSHOT.jar' & > /dev/null
```
