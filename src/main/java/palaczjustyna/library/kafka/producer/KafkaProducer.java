package palaczjustyna.library.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import palaczjustyna.library.email.domain.EmailToSendDTO;

@Slf4j
@Component
public class KafkaProducer {

    private final KafkaTemplate<String, EmailToSendDTO> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, EmailToSendDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Value(value = "${kafka.email.topic}")
    private String topicName;

    public void sendMessage(EmailToSendDTO message) {
        log.info("Sending Message to kafka. Message: {}",message);
        kafkaTemplate.send(topicName, message);
    }
}