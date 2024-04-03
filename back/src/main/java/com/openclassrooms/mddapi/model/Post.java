package com.openclassrooms.mddapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "POSTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le titre ne peut être vide")
    @Column(name = "title", length = 50)
    private String title;

    @NotNull(message = "Le content ne peut être vide")
    @Column(name = "content",  length = 2000)
    private String content;

    // Un post appartient à un utilisateur mais un utilisateur  peut avoir plusieurs post
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    // Un post appartient à un topic mais un topic peut avoir plusieurs post
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    // Un post peut avoir plusieurs commentaires mais un commentaire ne peut être lier qu'a un seul post
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;
}
