package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    private String createdAt;       // Date d'inscription

    // Liste des abonnements (peut être utile pour le profil utilisateur)
    private Set<SubscriptionDto> subscriptions;
}
