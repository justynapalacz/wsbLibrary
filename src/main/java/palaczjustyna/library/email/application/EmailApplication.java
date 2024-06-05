package palaczjustyna.library.email.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import palaczjustyna.library.email.domain.EmailService;
import palaczjustyna.library.email.domain.EmailToSendDTO;


/**
 * The EmailApplication class provides methods for sending emails to users.
 * It delegates the email sending functionality to the {@link EmailService}.
 *
 * <p>This class is annotated with {@link Service} to indicate that it is a service component
 * and {@link Slf4j} for logging purposes.</p>
 *
 * @see EmailService
 * @see EmailToSendDTO
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmailApplication {

    private final EmailService emailService;

    /**
     * Sends an email to the user based on the provided {@link EmailToSendDTO}.
     *
     * @param emailToSendDTO the {@link EmailToSendDTO} object containing the details of the email to be sent.
     */
    public void sendEmailToUser(EmailToSendDTO emailToSendDTO){
        log.info("Send emil: EmailDto: {} ", emailToSendDTO);
        emailService.sendEmailToUser(emailToSendDTO);
    }
}
