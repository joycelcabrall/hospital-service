package techchallenge3.hospitalservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/graphql/**").authenticated()
                        .requestMatchers("/graphiql/**").authenticated()

                        .requestMatchers("/usuarios/**").hasRole("MEDICO")

                        .requestMatchers("/consultas/**")
                        .hasAnyRole("MEDICO", "ENFERMEIRO", "PACIENTE")

                        .anyRequest().authenticated()
                )

                .headers(headers ->
                        headers.frameOptions(frame -> frame.disable())
                )

                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}