FROM openjdk:17-jdk
EXPOSE 8082
MAINTAINER pl.dawidkaszuba
COPY target/*.jar /image-service.jar

ENTRYPOINT ["java", "-jar", "/image-service.jar"]