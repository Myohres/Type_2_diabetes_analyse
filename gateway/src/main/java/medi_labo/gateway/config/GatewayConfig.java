package medi_labo.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
public class GatewayConfig {

    private final JwtUtils jwtUtils;

    public GatewayConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, ServiceUnavailableGatewayFilter serviceUnavailableFilter) {
        return builder.routes()
                .route("patient-information", r -> r.path("/pat-information/**")
                        .filters(f -> f.filter(jwtFilter()).filter(serviceUnavailableFilter))
                        .uri("http://localhost:8080"))
                .route("patient-history", r -> r.path("/pat-history/**")
                        .filters(f -> f.filter(jwtFilter()).filter(serviceUnavailableFilter))
                        .uri("http://localhost:8081"))
                .route("patient-assessment", r -> r.path("/pat-assessment/**")
                        .filters(f -> f.filter(jwtFilter()).filter(serviceUnavailableFilter))
                        .uri("http://localhost:8082"))
                .build();
    }

    private GatewayFilter jwtFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (request.getMethod() == HttpMethod.OPTIONS) {
                return chain.filter(exchange);
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            if (!jwtUtils.validateToken(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}

