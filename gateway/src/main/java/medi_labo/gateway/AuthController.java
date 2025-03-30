package medi_labo.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        // Dans une vraie appli, on vérifierait les identifiants en base de données
        if (username == null || username.isEmpty()) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur invalide");
        }

        // Générer un token JWT avec le username
        String token = JwtUtils.generateToken(username);
        return ResponseEntity.ok(token);
    }

    // Vérifie si un token JWT est valide
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        boolean isValid = JwtUtils.validateToken(token);
        if (isValid) {
            String username = JwtUtils.extractUsername(token);
            return ResponseEntity.ok("Token valide pour l'utilisateur : " + username);
        } else {
            return ResponseEntity.status(401).body("Token invalide ou expiré");
        }
    }
}


