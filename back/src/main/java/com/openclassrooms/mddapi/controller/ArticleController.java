package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Récupérer tous les articles
    @GetMapping
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.findAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Récupérer les articles d'un auteur
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<ArticleDto>> getArticlesByAuthor(@PathVariable Long authorId) {
        List<ArticleDto> articles = articleService.findArticlesByAuthorId(authorId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Créer un nouvel article (en récupérant l'utilisateur authentifié via le token JWT)
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto, Authentication authentication) {
        String username = authentication.getName(); // Récupérer l'utilisateur connecté
        ArticleDto createdArticle = articleService.saveArticle(articleDto, username);  // Associer l'article à l'utilisateur
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    // Mettre à jour un article
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        ArticleDto updatedArticle = articleService.updateArticle(articleDto);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    // Supprimer un article
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
