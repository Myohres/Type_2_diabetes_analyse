package medi_labo.gateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
@Component

public class JwtUtils {


    private String SECRET_KEY = "tjj,;fmuk,jnghbfdh5465265lkhuiyj";
    String encodedKey = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

    /**
     * Décode la clé qui est en encodé en base64.
     * @return une SecretKey nécéssaire à la validation du JWT token
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .claim("role", role)
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = parseClaims(token);
            Date expiration = claimsJws.getPayload().getExpiration();
            return (!expiration.before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
    }

    public String getRoleFromToken(String token) {
        return parseClaims(token).getPayload().get("role", String.class);
    }

}
