package pl.medrekkaszuba.imageservice.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.medrekkaszuba.imageservice.model.Image;
import pl.medrekkaszuba.imageservice.exception.KafkaSendingMessageException;

@Slf4j
@Service
public class KafkaPublisherService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String processedImagesTopic;

    private final ObjectMapper objectMapper;

    public KafkaPublisherService(KafkaTemplate<String, String> kafkaTemplate,
                                 @Value("${kafka.processedImagesTopic.name}") String processedImagesTopic,
                                 ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.processedImagesTopic = processedImagesTopic;
        this.objectMapper = objectMapper;
    }

    public void sendProcessedImage(Image processedImage) {
        sendKafkaMessage(processedImagesTopic, processedImage.getNewsItemId(), processedImage);
    }

    private void sendKafkaMessage(String topic, String key, Image image) {
        try {
            String serializedMessage = objectMapper.writeValueAsString(image);
            kafkaTemplate.send(topic, key, serializedMessage).get();
        } catch (Exception e) {
            log.error("[KafkaPublisherService] Error sending message to Kafka", e);
            throw new KafkaSendingMessageException("An error occurred while sending message to Kafka", e);
        }
    }
}
