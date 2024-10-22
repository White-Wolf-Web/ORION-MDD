package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Trouver un abonnement par nom
    Optional<Subscription> findByName(String name);

    // Vérifier si un abonnement existe par nom
    boolean existsByName(String name);
}
