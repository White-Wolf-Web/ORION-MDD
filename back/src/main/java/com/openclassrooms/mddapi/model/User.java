package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @ManyToMany(fetch = FetchType.LAZY)                           // Pour associer User et Topic
    @JoinTable(
            name = "user_subscriptions",                          // Le nom de la table d'association dans la base de données
            joinColumns = @JoinColumn(name = "user_id"),          // Définit la colonne "user_id" comme clé étrangère pour les utilisateurs
            inverseJoinColumns = @JoinColumn(name = "topic_id")   // Définit la colonne "topic_id" comme clé étrangère pour les thèmes (topics)
    )
    @JsonIgnore                                                   // Indique à Jackson (le convertisseur JSON de Spring) de ne pas sérialiser cette propriété lors de la conversion d'un objet en JSON
    private Set<Topic> subscriptions = new HashSet<>();           // Ensemble des abonnements d'un utilisateur
                                                                  // Set est choisi ici parce qu'il n'autorise pas les doublons
                                                                  // On initialise la collection avec HashSet pour éviter les erreurs de nullité avant l'ajout d'un abonnement

}
