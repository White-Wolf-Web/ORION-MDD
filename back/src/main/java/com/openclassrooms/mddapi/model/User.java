package com.openclassrooms.mddapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @NotNull(message = "Email ne peut pas être null")
    private String email;

    @Column(name = "name")
    @NotNull(message = "Nom ne peut pas être null")
    private String name;

    @Column(name = "password")
    @NotNull(message = "Mot de passe ne peut pas être null")
    private String password;

    // Un utilisateur peut avoir plusieurs posts
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();

    // Un utilisateur peut avoir plusieurs commentaires
    @OneToMany(mappedBy = "author")
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "created_at")
    // L'attribut name dans l'annotation @Column spécifie explicitement le nom de la colonne dans la BDE.
    @Temporal(TemporalType.TIMESTAMP)       //  TIMESTAMP signifie qu'il enregistre la date et l'heure.
    private Date created_at;                 // le champ Java createdAt doit être mappé à la colonne nommée created_at dans la table de base de données. On se doit de respecter ici le CamelCase

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    // Spécifie le type de la colonne dans la base de données pour stocker les dates
    private Date updated_at;
}

