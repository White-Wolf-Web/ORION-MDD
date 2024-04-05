package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {
    private Long id;

    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(min = 5, max = 100, message = "Le titre doit contenir entre 5 et 100 caractères")
    private String title;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    @Size(min = 20, max = 2000, message = "Doit contenir entre 20 et 2000 caractères")
    private String content;

    private UserDto author;
    private Date createdAt;
    private Date updatedAt;

}