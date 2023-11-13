server:
    port: 8081

spring:
    kafka:
        bootstrap-servers: localhost:9092
        consumer.groupId: group

kafka:
    imagesToProcessTopic:
        name: imagesToProcessTopic
        partitions: 2
        replicationFactor: 1
    processedImagesTopic:
        name: processedImagesTopic
        partitions: 2
        replicationFactor: 1