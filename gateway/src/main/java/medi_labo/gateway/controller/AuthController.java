package medi_labo.gateway.controller;


import jakarta.validation.Valid;
import medi_labo.gateway.model.User;
import medi_labo.gateway.config.JwtUtils;
import medi_labo.gateway.model.dto.LoginRequest;
import medi_labo.gateway.model.dto.UserConnectedDTO;
import medi_labo.gateway.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://vue-app:5173/")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final JwtUtils jwtUtils;

    public AuthController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    private UserService userService;


    @GetMapping("/{login}")
    public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {
        log.info("GET/user/{}", login);
        try {
            return ResponseEntity.ok(userService.findUserByLogin(login));
        } catch (NoSuchElementException e) {
            log.error("GetUserByLogin error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserConnectedDTO> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("POST /login/{}{}" , loginRequest.getLogin(), loginRequest.getPassword());
        try {
            return ResponseEntity.ok(userService.authenticateAndGenerateToken(loginRequest.getLogin(), loginRequest.getPassword()));
        } catch (NoSuchElementException e) {
            log.error("login error {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            log.error("Login error {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("POST/createUser {}", user);
        try {
            return ResponseEntity.ok(userService.addUser(user));
        } catch (ResponseStatusException e) {
            log.error("CreateUser error : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(
            @RequestParam String login,
            @RequestBody User user) {
        log.info("PUT/updateUser {}{}", login, user);
        try {
            return ResponseEntity.ok(userService.updateUser(login, user));
        } catch (NoSuchElementException e) {
            log.error("UpdateUser error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam String login) {
        log.info("DELETE/deleteUser {}", login);
        try {
            userService.deleteUser(login);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            log.error("DeleteUser error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        try {
            boolean isValid = jwtUtils.validateToken(token);
            if (isValid) {
                String extractedUsername = jwtUtils.extractUsername(token);
                return ResponseEntity.ok("Token valide pour l'utilisateur : " + extractedUsername);
            } else {
                return ResponseEntity.status(401).body("Token invalide ou expiré");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erreur lors de la validation du token : " + e.getMessage());
        }
    }
}



