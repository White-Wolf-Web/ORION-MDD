package com.openclassrooms.mddapi.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "strongpassword";  // Remplace par ton mot de passe
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Mot de passe encodé : " + encodedPassword);
    }
}
