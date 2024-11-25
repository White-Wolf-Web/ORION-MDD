package com.openclassrooms.mddapi.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {

    private final SecretKey jwtSecret;                               // Clé secrète utilisée pour signer et vérifier les tokens JWT

    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationMs;                                    // Durée de validité des tokens


    // Il initialise la clé secrète en utilisant une valeur définie
    public JwtTokenUtil(@Value("${app.jwtSecret}") String secret) {
        this.jwtSecret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Génère un token JWT à partir d'un email et d'un nom d'utilisateur.
    public String generateToken(String email, String username) {
        return Jwts.builder()
                .setSubject(email)               // Définit l'email comme sujet principal du token.
                .claim("username", username)  // Ajoute un claim personnalisé pour le nom d'utilisateur.
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();                      // Finalise la création du token sous forme de chaîne compacte.
    }

    // Extrait l'email du token en tant que sujet principal.
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();  // Récupère le champ qui contient l'email.
    }

    // Extrait le nom d'utilisateur depuis un claim personnalisé dans le token.
    public String getUsernameFromToken(String token) {
        return (String) getClaimsFromToken(token).get("username");  // Récupère la valeur du claim "username".
    }

    // Méthode privée pour extraire toutes les données (claims) du token.
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)           // Définit la clé secrète utilisée pour vérifier le token.
                .build()
                .parseClaimsJws(token)              // Analyse et vérifie le token JWT.
                .getBody();                         // Retourne le corps des claims (données) du token.
    }

    // Valide le token JWT pour vérifier s'il est correct et non expiré.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println("Token invalide : " + e.getMessage());
            return false;
        }
    }
}