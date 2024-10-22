package com.openclassrooms.mddapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Encode le mot de passe en utilisant BCrypt
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // Vérifie si le mot de passe brut correspond au mot de passe encodé
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
