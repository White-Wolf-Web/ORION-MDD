package com.openclassrooms.mddapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // On utilise Logger pour enregistrer des informations dans les journaux d'application.
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenUtil jwtTokenUtil;                   // Pour gérer les tokens
    private final CustomUserDetailsService userDetailsService; // Pour charger les informations utilisateur.

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    // Méthode principale exécutée pour chaque requête HTTP.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization"); // Récupère l'en-tête "Authorization" de la requête, où le token JWT est normalement envoyé.
        String token = null;
        String email = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {  // Vérifie si l'en-tête "Authorization" commence par "Bearer ".
            token = header.substring(7);             // Extrait le token en ignorant le préfixe "Bearer ".
            try {                                               // Tente d'extraire l'email et le nom d'utilisateur du token.
                email = jwtTokenUtil.getEmailFromToken(token);
                username = jwtTokenUtil.getUsernameFromToken(token);
            } catch (Exception e) {
                logger.error("Erreur lors de l'extraction de l'email ou du nom d'utilisateur depuis le token JWT: {}", e.getMessage());
            }
        }

        // Si un email ou un nom d'utilisateur est extrait et qu'il n'est pas encore authentifié.
        if ((email != null || username != null) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String principal = email != null ? email : username;
            if (jwtTokenUtil.validateToken(token)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(principal);         // Alors on charge les détails utilisateur depuis la BDD.
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken( // Puis on crée un objet d'authentification avec les rôles de l'utilisateur.
                        userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);                         // Et enfin on définit cet utilisateur comme authentifié dans le contexte de sécurité.
                logger.info("Authentification réussie pour l'utilisateur avec identifiant: {}", principal);
            } else {
                logger.warn("Token JWT invalide pour l'identifiant: {}", principal);
            }
        }

        filterChain.doFilter(request, response);                                                    // Et on passe la requête au prochain filtre dans la chaîne.
    }


    // Spécifie que ce filtre ne s'applique pas aux routes "/auth/register" et "/auth/login".
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/auth/register") || request.getServletPath().equals("/auth/login");
    }
}

