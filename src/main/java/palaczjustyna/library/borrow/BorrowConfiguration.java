package palaczjustyna.library.borrow;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"palaczjustyna.library.borrow.infrastructure"})
public class BorrowConfiguration {
}
