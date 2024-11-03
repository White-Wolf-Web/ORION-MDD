package com.openclassrooms.mddapi.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionDto {

    private Long id;
    private String name;
    private String description;
    private Set<Long> userIds;  // Liste des IDs des utilisateurs abonnés
}
