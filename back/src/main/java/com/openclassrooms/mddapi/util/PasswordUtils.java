package com.openclassrooms.mddapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}

