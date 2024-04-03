package palaczjustyna.library.book;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"palaczjustyna.library.book.infrastructure"}
)
public class BookConfiguration {
}
