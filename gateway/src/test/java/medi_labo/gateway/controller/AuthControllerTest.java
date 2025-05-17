package medi_labo.gateway.controller;

import medi_labo.gateway.config.JwtUtils;
import medi_labo.gateway.model.Role;
import medi_labo.gateway.model.User;
import medi_labo.gateway.model.dto.UserConnectedDTO;
import medi_labo.gateway.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private  JwtUtils jwtUtils;



    String URL_GATEWAY = "/user";
    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");
    }

  /*  @Test
    void getUserByLogin() throws Exception {
        when(userService.findUserByLogin(any())).thenReturn(user);
        mockMvc.perform(get(URL_GATEWAY+"/login")).andExpect(status().isOk());
    }

    @Test
    void getUserByLoginNotFound() throws Exception {
        when(userService.findUserByLogin(any())).thenThrow(NoSuchElementException.class);
        mockMvc.perform(get(URL_GATEWAY+"/login")).andExpect(status().isNotFound());
    }

    @Test
    void login() throws Exception {
        when(userService.authenticateAndGenerateToken(any(),any())).thenReturn(new UserConnectedDTO());
        mockMvc.perform(post(URL_GATEWAY+"/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"login\" : \"admin\",\n" +
                        "    \"password\" : \"123\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    void loginNotFound() throws Exception {
        when(userService.authenticateAndGenerateToken(any(),any())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post(URL_GATEWAY+"/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"login\" : \"admin\",\n" +
                                "    \"password\" : \"123\"\n" +
                                "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void loginWrongPassword() throws Exception {
        when(userService.authenticateAndGenerateToken(any(),any())).thenThrow(new RuntimeException());
        mockMvc.perform(post(URL_GATEWAY+"/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"login\" : \"admin\",\n" +
                                "    \"password\" : \"123\"\n" +
                                "}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createUser() throws Exception {
    }

    @Test
    void updateUser() throws Exception {
    }

    @Test
    void deleteUser() throws Exception {
    }

    @Test
    void validateToken() throws Exception {
    }*/
}