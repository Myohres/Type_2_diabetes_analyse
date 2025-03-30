package medi_labo.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Ici, vous pourriez ajouter la logique pour valider le token JWT.
        // Par exemple, vérifiez si le token est présent dans l'en-tête "Authorization".
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || !isValidJwt(token)) {
            // Si le token est invalide, retournez une réponse d'erreur (par exemple 401 Unauthorized)
            return Mono.error(new RuntimeException("Invalid token"));
        }

        // Si le token est valide, continuez la chaîne de filtres
        return chain.filter(exchange);
    }

    private boolean isValidJwt(String token) {
        // Logique de validation du token JWT (par exemple, vérification du format, signature, etc.)
        return token.startsWith("Bearer ");
    }
}

