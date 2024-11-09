package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.projections.UserWithSubscriptions;

import com.openclassrooms.mddapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class UserRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSubscriptionsLoading() {
        User user = userRepository.findUserWithSubscriptionsByEmail("aaa@aaaaaaa")
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Vérifiez que les abonnements sont bien chargés
        user.getSubscriptions().forEach(subscription ->
                logger.info("Abonnement trouvé : {}", subscription.getName())
        );
    }
}
