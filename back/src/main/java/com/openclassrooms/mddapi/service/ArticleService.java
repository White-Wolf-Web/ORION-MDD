package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.model.Article;

import java.util.List;

public interface ArticleService {

    ArticleDto saveArticle(ArticleDto articleDto, String username);  // Associer l'article à l'utilisateur

    ArticleDto updateArticle(ArticleDto articleDto);  // Mettre à jour un article

    ArticleDto findArticleById(Long id);  // Trouver un article par ID

    List<ArticleDto> findAllArticles();  // Retourner une liste de ArticleDto

    List<ArticleDto> findArticlesByAuthorId(Long authorId);  // Trouver les articles par auteur

    void deleteArticle(Long id);  // Supprimer un article
}
