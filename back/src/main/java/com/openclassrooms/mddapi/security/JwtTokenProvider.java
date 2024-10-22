package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Génère une clé sécurisée avec HS256
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Expiration du token configurée via le fichier application.properties
    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Générer un token JWT basé sur l'authentification
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();  // Récupère l'email ou le nom d'utilisateur

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret, SignatureAlgorithm.HS256)  // Utilisation de la clé et spécification de l'algorithme
                .compact();
    }

    // Extraire le token JWT de l'en-tête Authorization
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // Supprimer le préfixe "Bearer "
        }
        return null;
    }

    // Extraire le nom d'utilisateur (username) à partir du token JWT
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()  // Utilisation de la nouvelle méthode parserBuilder
                .setSigningKey(jwtSecret)    // Utiliser la clé sécurisée
                .build()                     // Construire le parser JWT
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Valider le token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)  // Utiliser la clé sécurisée
                    .build()
                    .parseClaimsJws(token);   // Parser le token JWT
            return true;
        } catch (JwtException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }
}

