package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Trouver les commentaires par article
    List<Comment> findByArticleId(Long articleId);

    // Trouver les commentaires par utilisateur
    List<Comment> findByUserId(Long userId);
}
