package medi_labo.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("patient-information", r -> r.path("/pat-information/**")
                        .filters(f -> f.filter(new JwtAuthenticationFilter()))
                        .uri("http://localhost:8080"))
                .route("patient-history", r -> r.path("/pat-history/**")
                        .filters(f -> f.filter(new JwtAuthenticationFilter()))
                        .uri("http://localhost:8081"))
                .route("patient-assessment", r -> r.path("/pat-assessment/**")
                        .filters(f -> f.filter(new JwtAuthenticationFilter()))
                        .uri("http://localhost:8082"))
                .build();
    }
}

