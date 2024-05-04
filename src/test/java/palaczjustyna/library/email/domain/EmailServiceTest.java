package palaczjustyna.library.email.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private EmailService emailService;

    @Test
    void shouldSendEmail(){
        // given
        String from = "justynaFrom@gmail.com";
        String to  = "justynaTo@gmail.com";
        String subject = "test subject";
        String body = "body test";
        EmailToSendDTO emailToSendDTO = new EmailToSendDTO(from, to, subject, body);

        WebClient.RequestBodyUriSpec uriSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec headerSpec =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodyUriSpec bodySpec  =  mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec response = mock(WebClient.ResponseSpec.class);

        when(webClient.method(HttpMethod.POST)).thenReturn(uriSpec);
        when(uriSpec.uri("/createEmail")).thenReturn(bodySpec);
        when(headerSpec.header(any(),any())).thenReturn(headerSpec);
        when(bodySpec.body(any())).thenReturn(headerSpec);
        when(headerSpec.retrieve()).thenReturn(response);
        when(response.bodyToMono(String.class))
                .thenReturn(Mono.just("ok"));

        // when
        emailService.sendEmailToUser(emailToSendDTO);

        // then
        verify(webClient, times(1)).method(HttpMethod.POST);
    }

}