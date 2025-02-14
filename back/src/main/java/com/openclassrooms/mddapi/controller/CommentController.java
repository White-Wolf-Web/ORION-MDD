package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentCreationDTO;
import com.openclassrooms.mddapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/comments")
@Tag(name = "CommentController", description = "Gestion des commentaires")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Lister tous les commentaires d'un article")
    @GetMapping
    public ResponseEntity<?> getCommentsByArticleId(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok().body(commentService.getCommentsByArticleId(articleId));
    }

    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable("articleId") Long articleId, @RequestBody CommentCreationDTO commentDTO) {
        try {
            commentService.addComment(articleId, commentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
