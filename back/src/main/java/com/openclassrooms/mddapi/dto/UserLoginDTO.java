package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "L'identifiant est obligatoire")
    private String identifier;  // Peut être l'email ou le nom d'utilisateur

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;


}
