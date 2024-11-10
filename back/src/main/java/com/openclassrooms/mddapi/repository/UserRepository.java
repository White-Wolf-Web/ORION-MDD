package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"subscriptions"})
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserWithSubscriptionsByEmail(@Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM user_subscriptions WHERE user_id = :userId AND topic_id = :topicId", nativeQuery = true)
    int removeUserSubscription(@Param("userId") Long userId, @Param("topicId") Long topicId);

    @Query("SELECT COUNT(u) > 0 FROM User u JOIN u.subscriptions s WHERE u.id = :userId AND s.id = :topicId")
    boolean isUserSubscribedToTopic(@Param("userId") Long userId, @Param("topicId") Long topicId);

    Optional<User> findByEmail(String email);
}




