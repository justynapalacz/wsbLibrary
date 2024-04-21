package palaczjustyna.library.email.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {


    public void sendEmailToUser(String from, String to, String title, String body){
        log.info("Email send from: {} ", from);
        log.info("Email send to: {} ", to);
        log.info("Email title: {} ", title);
        log.info("Email body: {} ", body);
        //komunikacja z micorserwisem do wysyłania maili
    }

}
