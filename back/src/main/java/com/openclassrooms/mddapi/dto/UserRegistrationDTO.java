package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format de l'email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;
}
