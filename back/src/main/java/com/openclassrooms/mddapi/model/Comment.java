package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "COMMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Un commentaire appartient à un utilisateur mais un utilisateur peut avoir plusieurs commentaires
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    // Un commentaire appartient à un post mais un post peut avoir plusieurs commentaires
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

}