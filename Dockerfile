FROM openjdk:8u92-jre-alpine

LABEL org.opencontainers.image.authors="haneul.kim@data-dynamics.io"
LABEL title="KT MONITORING Server"
LABEL version="1.0.0-SNAPSHOT"
LABEL description="KT MONITORING Server build test"
COPY ./src/main/resources/logback.xml /opt/logback.xml
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
