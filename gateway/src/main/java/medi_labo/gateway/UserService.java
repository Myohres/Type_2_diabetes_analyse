package medi_labo.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Date;
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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), new ArrayList<>()
        );
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

    private static final String SECRET_KEY = "mySecretKey";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
