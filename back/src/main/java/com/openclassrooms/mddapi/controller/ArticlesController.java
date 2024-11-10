package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleCreationDTO;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@Tag(name = "ArticlesController", description = "Gestion des articles")
public class ArticlesController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticlesController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @Operation(summary = "Liste des articles")
    @GetMapping
    public ResponseEntity<?> getAllArticles() {
        try {
            return ResponseEntity.ok(articleService.getAllArticles());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Consulter un article par ID", description = "Récupère un article spécifique en fournissant son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Article trouvé"),
            @ApiResponse(responseCode = "404", description = "Article non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(
            @Parameter(description = "ID de l'article à consulter", required = true, example = "1")
            @PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(articleService.getArticleById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }



    @Operation(summary = "Créer un nouvel article")
    @PostMapping
    public ResponseEntity<?> createArticle(
            @RequestBody ArticleCreationDTO articleCreationDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User author = userService.findByEmail(userDetails.getUsername()); // Utilisation de findByEmail
            Article article = articleService.createArticle(articleCreationDTO, author);
            return ResponseEntity.status(HttpStatus.CREATED).body(article);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
