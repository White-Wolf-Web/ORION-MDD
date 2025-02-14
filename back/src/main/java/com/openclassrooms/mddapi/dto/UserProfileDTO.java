package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format de l'email invalide")
    private String email;

    private List<TopicDTO> subscriptions;

    private String password;

    // Constructeur supplémentaire sans le champ `password`
    public UserProfileDTO(String username, String email, List<TopicDTO> subscriptions) {
        this.username = username;
        this.email = email;
        this.subscriptions = subscriptions;
    }

}