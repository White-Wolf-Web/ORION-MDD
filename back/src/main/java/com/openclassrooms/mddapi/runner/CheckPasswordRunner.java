package com.openclassrooms.mddapi.runner;

import com.openclassrooms.mddapi.service.AuthServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CheckPasswordRunner implements CommandLineRunner {

    private final AuthServiceImpl authServiceImpl;

    public CheckPasswordRunner(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {
        authServiceImpl.checkPassword();  // Appelle la méthode ici
    }
}
