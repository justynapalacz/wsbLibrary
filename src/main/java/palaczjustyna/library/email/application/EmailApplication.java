package palaczjustyna.library.email.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.email.domain.EmailService;
import palaczjustyna.library.email.domain.EmailToSendDTO;

@Slf4j
@Service
public class EmailApplication {

    @Autowired
    private EmailService emailService;

    public void sendEmailToUser(EmailToSendDTO emailToSendDTO){
        log.info("Send emil: EmailDto: {} ", emailToSendDTO);
        emailService.sendEmailToUser(emailToSendDTO);
    }
}
