package pl.medrekkaszuba.imageservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import pl.medrekkaszuba.imageservice.model.Image;
import pl.medrekkaszuba.imageservice.exception.KafkaConsumingMessageException;
import pl.medrekkaszuba.imageservice.service.ImageService;

@Slf4j
@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper;

    private final ImageService imageService;

    public KafkaConsumer(ObjectMapper objectMapper, ImageService imageService) {
        this.objectMapper = objectMapper;
        this.imageService = imageService;
    }

    @KafkaListener(topics = "${kafka.imagesToProcessTopic.name}", groupId = "group_id")
    public void consumeImagesToProcess(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            Image image = objectMapper.readValue(message, Image.class);
            imageService.processImage(image);
        } catch (Exception e) {
            log.error("[KafkaConsumer] Error consuming message from Kafka topic {} : {}", topic, e.getStackTrace());
            throw new KafkaConsumingMessageException("An error occurred while consuming message from Kafka", e);
        }
    }
}
