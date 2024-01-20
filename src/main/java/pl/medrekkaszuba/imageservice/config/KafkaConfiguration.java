package pl.medrekkaszuba.imageservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    @Bean
    public NewTopic imagesToProcessTopic(@Value("${kafka.imagesToProcessTopic.name}") String topicName,
                                         @Value("${kafka.imagesToProcessTopic.partitions}") int partitions,
                                         @Value("${kafka.imagesToProcessTopic.replicationFactor}") int replicationFactor) {

        return prepareTopic(topicName, partitions, replicationFactor);
    }

    @Bean
    public NewTopic processedImagesTopic(@Value("${kafka.processedImagesTopic.name}") String topicName,
                                         @Value("${kafka.processedImagesTopic.partitions}") int partitions,
                                         @Value("${kafka.processedImagesTopic.replicationFactor}") int replicationFactor) {

        return prepareTopic(topicName, partitions, replicationFactor);
    }

    private NewTopic prepareTopic(String topicName, int partitions, int replicationFactor) {
        return new NewTopic(topicName, partitions, (short) replicationFactor);
    }
}
