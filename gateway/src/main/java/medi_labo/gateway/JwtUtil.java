package medi_labo.gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "mySuperSecretKey";  // Utilise une clé secrète pour signer les tokens

    // Générer un token JWT
    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)  // Met le nom d'utilisateur dans le sujet
                .withIssuedAt(new Date())  // Ajoute la date de création du token
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Expiration après 1 heure
                .sign(Algorithm.HMAC256(SECRET_KEY));  // Signe avec l'algorithme HMAC256
    }

    // Vérifier et valider un token JWT
    public static boolean validateToken(String token) {
        try {
            // Créer un vérificateur avec la même clé secrète
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);  // Vérifie le token

            return jwt.getSubject() != null;  // Si le sujet (username) est présent, c'est un token valide
        } catch (JWTVerificationException exception) {
            return false;  // Le token n'est pas valide
        }
    }

    // Extraire le sujet (nom d'utilisateur) du token
    public static String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();  // Récupère le nom d'utilisateur depuis le token
    }
}
