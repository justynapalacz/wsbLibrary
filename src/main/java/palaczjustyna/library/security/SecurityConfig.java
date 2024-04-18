package palaczjustyna.library.security;

import org.springframework.beans.factory.annotation.Autowired;
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
import palaczjustyna.library.user.application.UserApplication;
import palaczjustyna.library.user.domain.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserApplication userApplication;
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> readers = new ArrayList<>();
        UserDetails admin = User.withUsername("Justyna")
                .password(encoder.encode("Justyna"))
                .roles("ADMIN", "LIBRARIAN", "READER")
                .build();
        readers.add(admin);

        UserDetails librarian = User.withUsername("Marcin")
                .password(encoder.encode("Marcin"))
                .roles("LIBRARIAN", "READER")
                .build();
        readers.add(librarian);

        List<UserDTO>  userDTOList = userApplication.getAllUsers();
        userDTOList.forEach(userDTO -> {
            UserDetails reader = User.withUsername(userDTO.login())
                    .password(encoder.encode(userDTO.password()))
                    .roles("READER")
                    .build();
            readers.add(reader);
        });

        return new InMemoryUserDetailsManager(readers);
    }

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
