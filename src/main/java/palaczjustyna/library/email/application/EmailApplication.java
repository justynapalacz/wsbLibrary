package palaczjustyna.library.email.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.email.domain.EmailService;
import palaczjustyna.library.email.domain.EmailToSendDTO;

@Service
public class EmailApplication {

    @Autowired
    private EmailService emailService;

    public void sendEmailToUser(EmailToSendDTO emailToSendDTO){
        emailService.sendEmailToUser(emailToSendDTO);
    }
}
