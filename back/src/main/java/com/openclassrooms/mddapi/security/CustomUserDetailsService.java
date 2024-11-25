package com.openclassrooms.mddapi.security;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Recherche un utilisateur en utilisant son email ou éventuellement son nom
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(identifier)           // Recherche par son email
                .or(() -> userRepository.findByUsername(identifier)) // Chercher par nom d'utilisateur si email introuvable
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + identifier));


        // Cet objet contient l'email comme identifiant, le mot de passe, et une liste vide pour les autorisations (rôles).
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
