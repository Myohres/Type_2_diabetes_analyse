package medi_labo.gateway.config;

import medi_labo.gateway.config.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.HttpHeaders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@Import(SecurityConfig.class)
public class GatewayConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private JwtUtils jwtUtils;

    @BeforeEach
    void setup() {
        // Simule un token valide avec un rôle autorisé
        Mockito.when(jwtUtils.validateToken("valid-token")).thenReturn(true);
        Mockito.when(jwtUtils.getRoleFromToken("valid-token")).thenReturn("ADMIN");
    }


    @Test
    void shouldBlockAccessWhenTokenIsInvalid() {
        Mockito.when(jwtUtils.validateToken("invalid-token")).thenReturn(false);

        webTestClient.get()
                .uri("/pat-information/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldBlockAccessWhenRoleIsNotAuthorized() {
        Mockito.when(jwtUtils.getRoleFromToken("valid-token")).thenReturn("PATIENT");

        webTestClient.get()
                .uri("/pat-history/test")
                .header(HttpHeaders.AUTHORIZATION, "Bearer valid-token")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void shouldBlockAccessWhenAuthorizationHeaderIsMissing() {
        webTestClient.get()
                .uri("/pat-history/test")
                .exchange()
                .expectStatus().isForbidden();
    }
}
