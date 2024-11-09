package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.projections.UserWithSubscriptions;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"subscriptions"})
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserWithSubscriptionsByEmail(@Param("email") String email);
    Optional<User> findByEmail(String email);
}




