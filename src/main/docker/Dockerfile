FROM openjdk:17-jdk-slim
EXPOSE 8081
MAINTAINER pl.dawidkaszuba
COPY target/*.jar /image-service.jar

ENTRYPOINT ["java", "-jar", "/image-service.jar"]