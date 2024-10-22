package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Vérifie si un utilisateur existe par son email
    boolean existsByEmail(String email);

    // Vérifie si un utilisateur existe par son nom d'utilisateur
    boolean existsByUsername(String username);
}
