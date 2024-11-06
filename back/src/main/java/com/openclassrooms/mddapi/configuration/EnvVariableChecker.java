package com.openclassrooms.mddapi.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EnvVariableChecker {

    @Value("${app.jwtSecret:undefined}")
    private String jwtSecret;

    @PostConstruct
    public void checkEnvVariable() {
        System.out.println("Valeur de JWT_SECRET : " + jwtSecret);
    }
}
