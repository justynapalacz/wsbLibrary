package palaczjustyna.library.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EmailConfiguration {
    @Value("${emailsander.url}")
    private String emailsanderURL;
    @Bean
    public WebClient webClientForEmailSender() {
        return WebClient.builder().baseUrl(emailsanderURL).build();
    }
}
