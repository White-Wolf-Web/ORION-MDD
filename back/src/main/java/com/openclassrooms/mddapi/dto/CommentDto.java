package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotBlank(message = "Comment content is required")
    private String content;

    private String authorUsername;  // Nom de l'auteur du commentaire

    private String createdAt;       // Format de la date

    private Long articleId;
}
