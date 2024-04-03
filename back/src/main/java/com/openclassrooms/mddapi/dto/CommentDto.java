package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public class CommentDto {
    private Long id;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    private String content;
    private UserDto author; //  On utilise UserDTO pour inclure les informations sur l'auteur
    private Date createdAt;
    private Date updatedAt;

}
