package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.security.CustomUserDetailsService;
import com.openclassrooms.mddapi.security.JwtAuthenticationEntryPoint;
import com.openclassrooms.mddapi.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;


    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationEntryPoint unauthorizedHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;    // Gère les détails des utilisateurs.
        this.unauthorizedHandler = unauthorizedHandler;              // Gère les erreurs d'authentification.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Désactiver CSRF et configurer la gestion des sessions
        http.csrf(AbstractHttpConfigurer::disable)

                // Les sessions sont configurées comme sans état car l'application utilise des tokens JWT.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configurer la gestion des erreurs d'authentification.
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        // Configurer les autorisations d'accès pour différentes requêtes. elles sont définies par chemin et méthode HTTP.
        http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/articles/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/users/me/**").authenticated().
                requestMatchers(HttpMethod.GET, "/api/users/subscriptions/**").authenticated()
                .requestMatchers(HttpMethod.GET, "api/topics").authenticated()
                .anyRequest().authenticated());

        // Retourner la configuration de la chaîne de filtres de sécurité.
        return http.build();
    }

    // On fournit un encodeur de mots de passe utilisant BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Configurer le gestionnaire d'authentification.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // `customUserDetailsService` est utilisé pour récupérer les infos des utilisateurs depuis la base de données.
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)

                // `passwordEncoder()` permet de vérifier les mots de passe
                .passwordEncoder(passwordEncoder());

        // On crée et retourne le gestionnaire d'authentification.
        return authenticationManagerBuilder.build();
    }
}
