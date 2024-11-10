package com.openclassrooms.mddapi.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Génère un token JWT basé sur l'identifiant de l'utilisateur (username ou email)
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Déprécié mais fonctionnel
                .setIssuedAt(new Date())  // Déprécié mais fonctionnel
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)  // Méthode moderne pour signer avec une clé
                .compact();
    }

    // Valide le token JWT
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()  // Méthode parserBuilder() est disponible dans 0.12.3
                    .setSigningKey(getSigningKey())  // Utilisation de la clé correcte
                    .build()
                    .parseClaimsJws(token);  // Parse et valide le token
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Gestion des exceptions liées au token JWT
            System.out.println("Invalid JWT: " + e.getMessage());
        }
        return false;
    }

    // Récupère l'username à partir du token JWT
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Méthode utilitaire pour générer la clé de signature
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);  // Décodage de la clé secrète en Base64
        return Keys.hmacShaKeyFor(keyBytes);  // Génération de la clé de signature
    }
}

