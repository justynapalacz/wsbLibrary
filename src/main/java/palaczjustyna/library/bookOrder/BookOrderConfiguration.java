package palaczjustyna.library.bookOrder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BookOrderConfiguration {

    @Value("${warehouse.url}")
    private String warehouseURL;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(warehouseURL).build();
    }
}
