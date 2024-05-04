package palaczjustyna.library.email.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import palaczjustyna.library.email.domain.EmailService;
import palaczjustyna.library.email.domain.EmailToSendDTO;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailApplicationTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailApplication emailApplication;

    @Test
    void shoudSendEmailToUser(){
        // given
        String from = "justynaFrom@gmail.com";
        String to  = "justynaTo@gmail.com";
        String subject = "test subject";
        String body = "body test";
        EmailToSendDTO emailToSendDTO = new EmailToSendDTO(from, to, subject, body);

        // when
        emailApplication.sendEmailToUser(emailToSendDTO);

        // then
        verify(emailService, times(1)).sendEmailToUser(emailToSendDTO);
    }
}