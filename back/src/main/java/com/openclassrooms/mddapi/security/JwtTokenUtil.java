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

    private final SecretKey jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private long jwtExpirationMs;

    public JwtTokenUtil(@Value("${app.jwtSecret}") String secret) {
        this.jwtSecret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String email, String username) {
        return Jwts.builder()
                .setSubject(email)  // Email comme sujet principal
                .claim("username", username)  // Nom d'utilisateur comme claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();  // Le sujet est l'email
    }

    public String getUsernameFromToken(String token) {
        return (String) getClaimsFromToken(token).get("username");  // Claim du nom d'utilisateur
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
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