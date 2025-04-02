package medi_labo.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class ServiceUnavailableFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(WebClientRequestException.class, e -> {
                    exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                    byte[] bytes = "Le service est indisponible. Merci de réessayer plus tard.".getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                });
    }

    @Override
    public int getOrder() {
        return -1; // Priorité haute
    }
}