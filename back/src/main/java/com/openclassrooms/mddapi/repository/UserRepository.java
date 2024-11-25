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

    @EntityGraph(attributePaths = {"subscriptions"})                                     // Cette annotation optimise les requêtes en demandant explicitement à JPA de charger les relations spécifiées
    @Query("SELECT u FROM User u WHERE u.email = :email")                                // Une requête JPQL pour récupérer un utilisateur (User) basé sur son email.
    Optional<User> findUserWithSubscriptionsByEmail(@Param("email") String email);       // Retourne un Optional<User> pour indiquer que le résultat peut être vide (si l'email n'existe pas).

    @Modifying                                                                                                            // Nous indique que la requête modifie les données dans la base de données (insert, update, delete).
    @Query(value = "DELETE FROM user_subscriptions WHERE user_id = :userId AND topic_id = :topicId", nativeQuery = true)  // Requête SQL native (pas JPQL) pour supprimer une relation entre un utilisateur (user_id) et un thème (topic_id) en simple si
    int removeUserSubscription(@Param("userId") Long userId, @Param("topicId") Long topicId);                             // La méthode retourne un entier (int) indiquant  Si 0, User n'était pas abonné à ce thème. Si 1, l'abonnement a été supprimé avec succès.

    @Query("SELECT COUNT(u) > 0 FROM User u JOIN u.subscriptions s WHERE u.id = :userId AND s.id = :topicId")  // Requête JPQL pour vérifier si un utilisateur est abonné à un thème spécifique ou pas
                                                                                                               // COUNT(u) > 0 retourne un booléen, true si l'utilisateur est abonné au thème, false sinon.
                                                                                                               //Combine l'utilisateur (u) avec ses abonnements (s) grâce à une jointure.
    boolean isUserSubscribedToTopic(@Param("userId") Long userId, @Param("topicId") Long topicId);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}




