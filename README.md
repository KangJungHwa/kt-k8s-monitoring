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

timezone 확인
```$xslt
select @@global.time_zone, @@session.time_zone
```
JDBC URL timezone 설정
```$xslt
jdbc:log4jdbc:mysql://10.233.4.179:3306/nlu?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul&rewriteBatchedStatements=true
```


패스워드 변경작업
```$xslt
UPDATE mysql.user SET Password=PASSWORD('ktaicc@1234') WHERE User='root';

GRANT USAGE ON *.* TO 'root'@'localhost' IDENTIFIED BY 'xxxxxxx';
GRANT USAGE ON *.* TO 'root'@'%' IDENTIFIED BY 'xxxxxxx';


FLUSH PRIVILEGES;
```

command line 실행 명령
```$xslt
nohup java -jar 'kt-k8s-monitoring-1.0.0-SNAPSHOT.jar' & > /dev/null
```
