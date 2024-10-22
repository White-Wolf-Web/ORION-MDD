package com.openclassrooms.mddapi.repository;
import com.openclassrooms.mddapi.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Trouver les articles par auteur
    List<Article> findByAuthorId(Long authorId);

    // Trouver les articles par thème (subscription)
    List<Article> findByTopicId(Long topicId);

    // Trouver les articles en fonction de la date de création (ordre décroissant)
    List<Article> findAllByOrderByCreatedAtDesc();
}
