package medi_labo.gateway.service;


import medi_labo.gateway.model.User;
import medi_labo.gateway.repository.UserRepository;
import medi_labo.gateway.config.JwtUtils;
import medi_labo.gateway.model.dto.UserConnectedDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public User findUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return optionalUser.get();

        } else {
            throw new NoSuchElementException("User not found with login: " + login);
        }
    }

    public UserConnectedDTO authenticateAndGenerateToken(String login, String password) {
        User user = findUserByLogin(login);


        if (!Objects.equals(user.getPassword(), password)) {
            throw new RuntimeException("Mot de passe incorrect");
        }
       String token = jwtUtils.generateToken(user.getLogin(), user.getRole().name());
        UserConnectedDTO userConnectedDTO = new UserConnectedDTO();
        userConnectedDTO.setLogin(user.getLogin());
        userConnectedDTO.setFirstName(user.getFirstName());
        userConnectedDTO.setLastName(user.getLastName());
        userConnectedDTO.setRole(user.getRole().name());
        userConnectedDTO.setToken(token);
        return userConnectedDTO;
    }


    public User addUser(User user) {
        if (checkLoginFree(user.getLogin())) {
            return userRepository.save(user);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Login is already taken: " + user.getLogin());
    }

    public boolean checkLoginFree(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.isEmpty();
    }

    public boolean checkPassword(String login, String password) {
        User user = findUserByLogin(login);
        return user.getPassword().equals(password);
    }

    public User updateUser(String login , User user) {
        User updatedUser = findUserByLogin(login);
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setRole(user.getRole());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(String login) {
        User user = findUserByLogin(login);
        userRepository.delete(user);
    }


}
