package palaczjustyna.library.email.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import palaczjustyna.library.kafka.producer.KafkaProducer;

/**
 * The EmailService class provides methods for sending emails to users.
 * It utilizes a WebClient to communicate with the email sender service.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see WebClient
 * @see EmailToSendDTO
 * @see KafkaProducer
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    @Qualifier("webClientForEmailSender")
    private WebClient webClient;

    @Autowired
    private KafkaProducer kafkaProducer;

    /**
     * Sends an email to the user based on the provided {@link EmailToSendDTO}.
     * It communicates with the email sender service using a WebClient.
     * If communication with the service fails, it falls back to sending the email via Kafka.
     *
     * @param emailToSendDTO the {@link EmailToSendDTO} object containing the details of the email to be sent.
     */
    public void sendEmailToUser(EmailToSendDTO emailToSendDTO) {
        log.info("Email send from: {} ", emailToSendDTO.from());
        log.info("Email send to: {} ", emailToSendDTO.to());
        log.info("Email subject: {} ", emailToSendDTO.subject());
        log.info("Email body: {} ", emailToSendDTO.body());

        try {
            String message = webClient
                    .method(HttpMethod.POST)
                    .uri("/createEmail")
                    .body(BodyInserters.fromValue(emailToSendDTO))
                    .header("Accept", "application/json, text/plain, */*")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Message from EmailSander: {}", message);
        } catch (Exception exception) {
            log.error("Problem with communication to EmailSender. Exception: {}", exception.getMessage());
            log.info("Send email by kafka mechanism.");
            kafkaProducer.sendMessage(emailToSendDTO);
        }
    }

}
