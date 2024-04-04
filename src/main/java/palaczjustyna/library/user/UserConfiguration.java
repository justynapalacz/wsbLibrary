package palaczjustyna.library.user;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"palaczjustyna.library.user.infrastructure"}
)
public class UserConfiguration {
}
