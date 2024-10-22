package com.openclassrooms.mddapi.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class SubscriptionDto {

    private Long id;
    private String name;  // Nom du sujet
    private Set<Long> userIds;  // Liste des IDs des utilisateurs abonnés
}
