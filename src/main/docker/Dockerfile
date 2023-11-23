FROM openjdk:17

EXPOSE 8080
COPY *.jar /opt/image-service.jar
WORKDIR /

RUN echo 'Main-Class: pl.medrekkaszuba.imageservice.ImageServiceApplication' > /opt/MANIFEST.MF
RUN jar cmf /opt/MANIFEST.MF /opt/image-service.jar

ENTRYPOINT java $JAVA_OPTS -jar "/opt/image-service.jar"

