package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.model.Article;

import java.util.List;

public interface ArticleService {

    ArticleDto saveArticle(ArticleDto articleDto, String username);  // Associer l'article à l'utilisateur

    // Trouver un article par ID
    ArticleDto findArticleById(Long id);

    List<ArticleDto> findAllArticles();  // Retourner une liste des Articles


}
