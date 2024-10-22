package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public SubscriptionDto saveSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = convertToSubscription(subscriptionDto);
        return convertToSubscriptionDto(subscriptionRepository.save(subscription));
    }

    @Override
    public List<SubscriptionDto> findAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map(this::convertToSubscriptionDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public SubscriptionDto updateSubscription(SubscriptionDto subscriptionDto) {
        Subscription existingSubscription = subscriptionRepository.findById(subscriptionDto.getId())
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + subscriptionDto.getId()));

        existingSubscription.setName(subscriptionDto.getName());

        Set<User> users = subscriptionDto.getUserIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)))
                .collect(Collectors.toSet());
        existingSubscription.setUsers(users);

        return convertToSubscriptionDto(subscriptionRepository.save(existingSubscription));
    }

    @Override
    @Transactional  // Ajouter l'annotation @Transactional ici
    public SubscriptionDto subscribeToTopic(SubscriptionDto subscriptionDto, String email) {
        // Récupérer l'utilisateur connecté par son email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Récupérer l'abonnement au sujet spécifié dans le DTO
        Subscription subscription = subscriptionRepository.findById(subscriptionDto.getId())
                .orElseThrow(() -> new RuntimeException("Le sujet (topic) spécifié n'existe pas"));

        // Associer l'utilisateur connecté à cet abonnement (ajouter à la liste des utilisateurs abonnés)
        subscription.getUsers().add(user);

        // Sauvegarder l'abonnement avec l'utilisateur ajouté
        subscriptionRepository.save(subscription);

        // Retourner l'abonnement mis à jour sous forme de DTO
        return convertToSubscriptionDto(subscription);
    }



    // Conversion Subscription -> SubscriptionDto
    private SubscriptionDto convertToSubscriptionDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setName(subscription.getName());

        Set<Long> userIds = subscription.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        subscriptionDto.setUserIds(userIds);

        return subscriptionDto;
    }

    // Conversion SubscriptionDto -> Subscription
    private Subscription convertToSubscription(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionDto.getId());
        subscription.setName(subscriptionDto.getName());

        Set<User> users = subscriptionDto.getUserIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId)))
                .collect(Collectors.toSet());
        subscription.setUsers(users);

        return subscription;
    }
}
