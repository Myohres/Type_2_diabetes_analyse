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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
public class GatewayConfig {

    private final JwtUtils jwtUtils;

    public GatewayConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    private static final Map<String, List<String>> routeRoles = Map.of(
            "/pat-assessment", List.of("ADMIN", "PRATICIEN"),
            "/pat-history", List.of("ADMIN", "PRATICIEN"),
            "/pat-information", List.of("ADMIN", "ORGANISATEUR", "PRATICIEN")
    );

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, ServiceUnavailableGatewayFilter serviceUnavailableFilter) {
        return builder.routes()
                .route("patient-information", r -> r.path("/pat-information/**")
                        .filters(f -> f.filter(jwtFilter()).filter(serviceUnavailableFilter))
                        .uri("http://patient-information:8080"))
                .route("patient-history", r -> r.path("/pat-history/**")
                        .filters(f -> f.filter(jwtFilter()).filter(serviceUnavailableFilter))
                        .uri("http://patient-history:8081"))
                .route("patient-assessment", r -> r.path("/pat-assessment/**")
                        .filters(f -> f.filter(jwtFilter()).filter(serviceUnavailableFilter))
                        .uri("http://patient-assessment:8082"))
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

            String role = jwtUtils.getRoleFromToken(token);
            String path = request.getPath().toString();

            Optional<Map.Entry<String, List<String>>> matched = routeRoles.entrySet().stream()
                    .filter(entry -> path.startsWith(entry.getKey()))
                    .findFirst();

            if (matched.isPresent()) {
                if (!matched.get().getValue().contains(role)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }
            } else {

                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}

