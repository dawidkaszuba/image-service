FROM openjdk:17

EXPOSE 8080
COPY *.jar /opt/image-service.jar
WORKDIR /
ENTRYPOINT java $JAVA_OPTS -jar "/opt/image-service.jar"

