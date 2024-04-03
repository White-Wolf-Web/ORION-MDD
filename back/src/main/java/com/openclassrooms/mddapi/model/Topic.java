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
@Table(name = "TOPICS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le titre ne peut être vide")
    @Column(name = "title", length = 50)
    private String title;

    @NotNull(message = "Le content ne peut être vide")
    @Column(name = "content",  length = 2000)
    private String content;

    // Un topic peut avoir plusieurs posts
    @OneToMany(mappedBy = "topic")
    private Set<Post> posts = new HashSet<>();

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

}
