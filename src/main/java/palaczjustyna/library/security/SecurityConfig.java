package palaczjustyna.library.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import palaczjustyna.library.employee.application.EmployeeApplication;
import palaczjustyna.library.employee.domain.Employee;
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The SecurityConfig class configures security settings for the application.
 * It is annotated with {@link Configuration} to indicate that it is a configuration class,
 * {@link EnableWebSecurity} to enable Spring Security's web security features,
 * and {@link EnableMethodSecurity} to enable method-level security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserApplication userApplication;

    private final EmployeeApplication employeeApplication;

    public SecurityConfig(UserApplication userApplication, EmployeeApplication employeeApplication) {
        this.userApplication = userApplication;
        this.employeeApplication = employeeApplication;
    }

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.password}")
    private String adminPassword;

    /**
     * Configures the user details service, which provides user authentication details.
     *
     * @param encoder the password encoder used to encode passwords
     * @return a {@link UserDetailsService} implementation
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> users = new ArrayList<>();

        UserDetails superAdmin = User.withUsername(adminName)
                .password(encoder.encode(adminPassword))
                .roles(getAdminRoles())
                .build();
        users.add(superAdmin);

        List<Employee> adminList = employeeApplication.findEmployeeByRole(SecurityRoles.ADMIN);
        adminList.forEach(admin->{
            UserDetails adminRole = User.withUsername(admin.getLogin())
                    .password(encoder.encode(admin.getPassword()))
                    .roles(getAdminRoles())
                    .build();
            users.add(adminRole);
        });

        List<Employee> librarianList = employeeApplication.findEmployeeByRole(SecurityRoles.LIBRARIAN);
        librarianList.forEach(librarian->{
            UserDetails librarianRole = User.withUsername(librarian.getLogin())
                    .password(encoder.encode(librarian.getPassword()))
                    .roles(getLibrarianRoles())
                    .build();
            users.add(librarianRole);
        });

        List<UserDTO>  userDTOList = userApplication.getAllUsers();
        userDTOList.forEach(userDTO -> {
            UserDetails reader = User.withUsername(userDTO.login())
                    .password(encoder.encode(userDTO.password()))
                    .roles(getReaderRoles())
                    .build();
            users.add(reader);
        });

        return new InMemoryUserDetailsManager(users);
    }

    /**
     * Configures security filter chain.
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").authenticated()
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Creates a password encoder bean.
     *
     * @return a {@link PasswordEncoder} implementation
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] getAdminRoles(){
        return new String[]{SecurityRoles.ADMIN.toString(), SecurityRoles.LIBRARIAN.toString(), SecurityRoles.READER.toString()};
    }

    private String[] getLibrarianRoles(){
        return new String[]{SecurityRoles.LIBRARIAN.toString(), SecurityRoles.READER.toString()};
    }

    private String[] getReaderRoles(){
        return new String[]{SecurityRoles.READER.toString()};
    }
}
