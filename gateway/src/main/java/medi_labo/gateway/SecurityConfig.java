package medi_labo.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Nouvelle syntaxe pour désactiver CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/validate").permitAll()  // Routes publiques
                        .anyRequest().authenticated()  // Le reste nécessite une authentification
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // JWT = pas de session

        return http.build();
    }
}
