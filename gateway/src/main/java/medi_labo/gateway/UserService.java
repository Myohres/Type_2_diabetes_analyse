package medi_labo.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("User not found with login: " + login);
        }
    }

    public User addUser(User user) {
        return userRepository.save(user);
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

    public boolean deleteUser(String login) {
        User user = findUserByLogin(login);
        userRepository.delete(user);
        return true;
    }
}
