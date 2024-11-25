package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {


    // JPQL (Java Persistence Query Language) est similaire à SQL mais agit sur les entités Java, pas sur les tables.
    @Query("SELECT t FROM Topic t JOIN t.subscribedUsers u WHERE u = :user")
    List<Topic> findByUserSubscription(@Param("user") User user);
}

/*
* t est un alias pour Topic
* u est un alias qui représente chaque utilisateur abonné (User).
*  la source de donnée est  l'entité Topic
* On effectue une jointure entre l'entité Topic et sa collection subscribedUsers
* subscribedUsers est en relation dans l'entité Topic un @ManyToMany avec User
* WHERE u = :user : => Filtre les résultats pour ne récupérer que les Topic où l'utilisateur est  passé en paramètre (abonné))
* :user => est un paramètre nommé qui sera fourni à l'exécution via @Param("user")
 */