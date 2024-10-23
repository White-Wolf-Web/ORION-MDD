package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@Tag(name = "Articles", description = "Endpoints for managing articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Récupérer tous les articles
    @GetMapping
    @Operation(summary = "Get all articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all articles"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ArticleDto>> getAllArticles() {
        List<ArticleDto> articles = articleService.findAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Récupérer les articles d'un auteur
    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get articles by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved articles by author"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<ArticleDto>> getArticlesByAuthor(@PathVariable Long authorId) {
        List<ArticleDto> articles = articleService.findArticlesByAuthorId(authorId);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Créer un nouvel article (en récupérant l'utilisateur authentifié via le token JWT)
    @PostMapping
    @Operation(summary = "Create a new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a new article"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleDto articleDto, Authentication authentication) {
        String username = authentication.getName(); // Récupérer l'utilisateur connecté
        ArticleDto createdArticle = articleService.saveArticle(articleDto, username);  // Associer l'article à l'utilisateur
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    // Mettre à jour un article
    @PutMapping("/{id}")
    @Operation(summary = "Update an article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the article"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<ArticleDto> updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto) {
        ArticleDto updatedArticle = articleService.updateArticle(articleDto);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    // Supprimer un article
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the article"),
            @ApiResponse(responseCode = "404", description = "Article not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
