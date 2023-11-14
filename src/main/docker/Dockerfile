FROM openjdk:17

EXPOSE 8080
COPY ${project.artifactId}-${project.version}-exec.jar /opt/image-service.jar
WORKDIR /
ENTRYPOINT java $JAVA_OPTS -jar "/opt/image-service.jar"

