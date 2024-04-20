package palaczjustyna.library.employee;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = {"palaczjustyna.library.employee.infrastructure"})
public class EmployeeConfiguration {
}
