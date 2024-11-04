package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Vérifie si un utilisateur existe par son email
    boolean existsByEmail(String email);

    // Vérifie si un utilisateur existe par son nom d'utilisateur
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.email = :email")
    Optional<User> findUserWithSubscriptionsByEmail(@Param("email") String email);
}
