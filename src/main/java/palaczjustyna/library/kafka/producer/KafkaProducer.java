package palaczjustyna.library.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import palaczjustyna.library.email.domain.EmailToSendDTO;

@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, EmailToSendDTO> kafkaTemplate;
    @Value(value = "${kafka.email.topic}")
    private String topicName;

    public void sendMessage(EmailToSendDTO message) {
        log.info("Sending Message to kafka. Message: {}",message);
        kafkaTemplate.send(topicName, message);
    }
}