package medi_labo.gateway.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;



@Configuration

public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // Configuration des règles d'autorisation des requêtes
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/**").permitAll()  // Permet toutes les requêtes sans authentification
                )
                // Désactivation de la page de login par défaut (formLogin)
                .formLogin().disable()
                // Désactivation de l'authentification HTTP basique
                .httpBasic().disable();

        return http.build();

    }
}
