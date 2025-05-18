package medi_labo.gateway.controller;

import medi_labo.gateway.config.JwtUtils;
import medi_labo.gateway.config.security.SecurityConfig;
import medi_labo.gateway.model.Role;
import medi_labo.gateway.model.User;
import medi_labo.gateway.model.dto.LoginRequest;
import medi_labo.gateway.model.dto.UserConnectedDTO;
import medi_labo.gateway.repository.UserRepository;
import medi_labo.gateway.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebFluxTest(controllers = AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    void testGetUserByLogin_success() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Mockito.when(userService.findUserByLogin("login")).thenReturn(user);

        webTestClient.get()
                .uri("/user/login")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(u -> {
                    assertEquals("login", u.getLogin());
                });
    }

    @Test
    void testGetUserByLogin_NotFound() {

        Mockito.when(userService.findUserByLogin("login")).thenThrow(NoSuchElementException.class);

        webTestClient.get()
                .uri("/user/login")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testLogin_success() {
        LoginRequest request = new LoginRequest();
        request.setLogin("login");
        request.setPassword("123");
        UserConnectedDTO dto = new UserConnectedDTO();
        dto.setLogin("login");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setToken("token");

        Mockito.when(userService.authenticateAndGenerateToken("login", "123")).thenReturn(dto);

        webTestClient.post()
                .uri("/user/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserConnectedDTO.class)
                .value(res -> assertEquals("login", res.getLogin()));
    }

    @Test
    void testLogin_NotFound() {
        LoginRequest request = new LoginRequest();
        request.setLogin("login");
        request.setPassword("123");
        UserConnectedDTO dto = new UserConnectedDTO();
        dto.setLogin("login");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setToken("token");

        Mockito.when(userService.authenticateAndGenerateToken(any(),any())).thenThrow(NoSuchElementException.class);

        webTestClient.post()
                .uri("/user/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testLogin_NotAuthorized() {
        LoginRequest request = new LoginRequest();
        request.setLogin("login");
        request.setPassword("123");
        UserConnectedDTO dto = new UserConnectedDTO();
        dto.setLogin("login");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setToken("token");

        Mockito.when(userService.authenticateAndGenerateToken(any(),any())).thenThrow(RuntimeException.class);

        webTestClient.post()
                .uri("/user/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testAddUser_success() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Mockito.when(userService.addUser(any())).thenReturn(user);

        webTestClient.post()
                .uri("/user")
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAddUser_conflict() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Mockito.when(userService.addUser(any())).thenThrow(ResponseStatusException.class);

        webTestClient.post()
                .uri("/user")
                .bodyValue(user)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Mockito.when(userService.updateUser(any(),any())).thenReturn(user);

        webTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/user")
                                .queryParam("login", "login")
                                .build())
                .bodyValue(user)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdateUserNotFound() {
        User user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");

        Mockito.when(userService.updateUser(any(),any())).thenThrow(NoSuchElementException.class);

        webTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/user")
                                .queryParam("login", "login")
                                .build())
                .bodyValue(user)
                .exchange()
                .expectStatus().isNotFound();
    }




    @Test
    void testValidateToken_valid() {
        String token = "valid.jwt.token";
        Mockito.when(jwtUtils.validateToken(token)).thenReturn(true);
        Mockito.when(jwtUtils.extractUsername(token)).thenReturn("login");

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/validate").queryParam("token", token).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(str -> assertTrue(str.contains("login")));
    }

    @Test
    void testValidateToken_NotValid() {
        String token = "valid.jwt.token";
        Mockito.when(jwtUtils.validateToken(token)).thenReturn(false);



        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/user/validate").queryParam("token", token).build())
                .exchange()
                .expectStatus().isUnauthorized();
    }


    @Test
    void testDeleteUser_Success() {
        String login = "login";


        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/user").queryParam("login", login).build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteUser_notFound() {
        String login = "inconnu";
        Mockito.doThrow(new NoSuchElementException("not found")).when(userService).deleteUser(login);

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/user").queryParam("login", login).build())
                .exchange()
                .expectStatus().isNotFound();
    }
}