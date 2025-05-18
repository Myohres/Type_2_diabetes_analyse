package medi_labo.gateway.service;

import medi_labo.gateway.config.JwtUtils;
import medi_labo.gateway.model.Role;
import medi_labo.gateway.model.User;
import medi_labo.gateway.model.dto.LoginRequest;
import medi_labo.gateway.model.dto.UserConnectedDTO;
import medi_labo.gateway.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    User user;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("login");
        user.setPassword("123");
        user.setRole(Role.ADMIN);
        user.setFirstName("firstName");
        user.setLastName("lastName");
    }

    @Test
    void findUserByLogin() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        User user1 = userService.findUserByLogin("login");
        assertEquals(user1.getLogin(), user.getLogin());
    }

    @Test
    void findUserByLoginNotFound() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()-> userService.findUserByLogin("1"));
    }

    @Test
    void authenticateAndGenerateToken() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        when(jwtUtils.generateToken(any(),any())).thenReturn("token");
        UserConnectedDTO userConnectedDTO = userService.authenticateAndGenerateToken(user.getLogin(),user.getPassword());
        assertEquals(userConnectedDTO.getToken(), "token");
        assertEquals(userConnectedDTO.getLogin(), user.getLogin());
        assertEquals(userConnectedDTO.getFirstName(), user.getFirstName());
        assertEquals(userConnectedDTO.getLastName(), user.getLastName());
        assertEquals(userConnectedDTO.getRole(), user.getRole().name());
    }

    @Test
    void authenticateAndGenerateTokenWrongPassword() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(),any())).thenReturn(false);
       assertThrows(RuntimeException.class, () -> userService.authenticateAndGenerateToken(user.getLogin(), user.getPassword()));
    }

    @Test
    void addUser() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);
        User userAdd = userService.addUser(user);
        assertEquals(userAdd, user);
    }

    @Test
    void addUserLoginNotFree() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        assertThrows(ResponseStatusException.class, ()-> userService.addUser(new User()));
    }

    @Test
    void checkLoginFree() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        assertFalse(userService.checkLoginFree("login"));
    }

    @Test
    void checkLoginFreeNotFree() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.empty());
        assertTrue(userService.checkLoginFree("login"));
    }

    @Test
    void checkPasswordTrue() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("login");
        loginRequest.setPassword("password");
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        assertTrue(userService.checkPassword(loginRequest.getLogin(),loginRequest.getPassword()));
    }

    @Test
    void checkPasswordFalse() {
        when(passwordEncoder.matches(any(),any())).thenReturn(false);
        assertFalse(userService.checkPassword("1","2"));
    }

    @Test
    void updateUser() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        User userInfoUpdate = new User();
        userInfoUpdate.setLogin("loginUp");
        userInfoUpdate.setPassword("123");
        userInfoUpdate.setLastName("lastNameUp");
        userInfoUpdate.setFirstName("firstNameUp");
        userInfoUpdate.setRole(Role.PRATICIEN);
        User userUpdated = userService.updateUser("login", userInfoUpdate );
        assertEquals(userUpdated.getLogin(),"login");
        assertEquals(userUpdated.getRole().name(), "PRATICIEN");
        assertEquals(userUpdated.getPassword(), "123");
        assertEquals(userUpdated.getLastName(), "lastNameUp");
        assertEquals(userUpdated.getFirstName(), "firstNameUp");
    }

    @Test
    void deleteUser() {
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(user));
        userService.deleteUser("1");
        verify(userRepository,times(1)).delete(any());
    }
}