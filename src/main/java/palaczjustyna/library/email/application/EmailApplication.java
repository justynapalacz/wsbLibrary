package palaczjustyna.library.email.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import palaczjustyna.library.email.domain.EmailService;

@Service
public class EmailApplication {

    @Autowired
    private EmailService emailService;

    public void sendEmailToUser(String from, String to, String title, String body){
        emailService.sendEmailToUser(from, to,title,body);
    }
}
