package com.openclassrooms.mddapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")  // Autorise les requêtes depuis Angular sur localhost:4200
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // Méthodes autorisées
                        .allowedHeaders("Authorization", "Cache-Control", "Content-Type")  // Headers autorisés
                        .allowCredentials(true)  // Autorise l'envoi de cookies, si nécessaire
                        .maxAge(3600);  // Temps en secondes pour la mise en cache de la requête de pré-vérification
            }
        };
    }
}
