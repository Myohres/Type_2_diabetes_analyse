package medi_labo.gateway.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;


class JwtUtilsTest {



    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "SECRET_KEY", "my-very-secret-key-for-testing-only-123456");

    }



    @Test
    void extractUsername() {
        String token = jwtUtils.generateToken("user","ROLE_USER" );

        String extractedUsername = jwtUtils.extractUsername(token);
        String extractedRole = jwtUtils.getRoleFromToken(token);

        assertEquals("user", extractedUsername);
        assertEquals("ROLE_USER", extractedRole);
    }

    @Test
    void validateToken() {
        String token = jwtUtils.generateToken("user","ROLE_USER");

       boolean validToken = jwtUtils.validateToken(token);
       assertTrue(validToken);
    }

    @Test
    void validateTokenWrong() {
        String token = "sdf";
        boolean validToken = jwtUtils.validateToken(token);
        assertFalse(validToken);
    }

    @Test
    void getRoleFromToken() {
        String token = jwtUtils.generateToken("user","ROLE_USER");
        String role = jwtUtils.getRoleFromToken(token);
        assertEquals("ROLE_USER", role);
    }
}