package palaczjustyna.library.email.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class EmailService {

    @Autowired
    @Qualifier("webClientForEmailSender")
    private WebClient webClient;

    public void sendEmailToUser(EmailToSendDTO emailToSendDTO) {
        log.info("Email send from: {} ", emailToSendDTO.from());
        log.info("Email send to: {} ", emailToSendDTO.to());
        log.info("Email subject: {} ", emailToSendDTO.subject());
        log.info("Email body: {} ", emailToSendDTO.body());

        String message = webClient
                .method(HttpMethod.POST)
                .uri("/createEmail")
                .body(BodyInserters.fromValue(emailToSendDTO))
                .header("Accept", "application/json, text/plain, */*")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Message from EmailSander: {}", message);
    }

}
