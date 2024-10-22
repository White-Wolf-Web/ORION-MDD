package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {

    // Sauvegarder un abonnement
    SubscriptionDto saveSubscription(SubscriptionDto subscriptionDto);

    // Trouver tous les abonnements
    List<SubscriptionDto> findAllSubscriptions();

    // Supprimer un abonnement par ID
    void deleteSubscription(Long id);

    // Mettre à jour un abonnement
    SubscriptionDto updateSubscription(SubscriptionDto subscriptionDto);

    // S'abonner à un sujet
    SubscriptionDto subscribeToTopic(SubscriptionDto subscriptionDto, String email);
}
