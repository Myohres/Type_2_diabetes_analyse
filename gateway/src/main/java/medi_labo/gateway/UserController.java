package medi_labo.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping("/signUpLogin/")
    public ResponseEntity<Boolean> checkLoginFree(@RequestParam String login) {
        log.info("GET/signUpLogin/{}", login);
        try {
            return ResponseEntity.ok(userService.checkLoginFree(login));
        } catch (Exception e) {
            log.error("CheckLoginFree error : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/connexion/")
    public ResponseEntity<Boolean> checkPassWord(
            @RequestParam String login,
            @RequestParam String password) {
        log.info("GET/connexion/{}{}", login, password);
        try {
            return ResponseEntity.ok(userService.checkPassword(login, password));
        } catch (NoSuchElementException e) {
            log.error("CheckPassWord error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("POST/createUser {}", user);
        try {
            return ResponseEntity.ok(userService.addUser(user));
        } catch (Exception e) {
            log.error("CreateUser error : {}", e.getMessage());
            return ResponseEntity.badRequest().build();
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
    public ResponseEntity<Boolean> deleteUser(@RequestParam String login) {
        log.info("DELETE/deleteUser {}", login);
        try {
            return ResponseEntity.ok(userService.deleteUser(login));
        } catch (NoSuchElementException e) {
            log.error("DeleteUser error : {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }







}
