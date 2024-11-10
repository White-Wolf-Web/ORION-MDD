package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "UsersController", description = "Gestion des utilisateurs et abonnements")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Consulter le profil utilisateur")
    @GetMapping("/me")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        logger.info("Requête reçue pour /users/me");
        try {
            if (userDetails == null) {
                logger.warn("Utilisateur non authentifié");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
            }
            UserProfileDTO profile = userService.getUserProfile();
            logger.info("Profil utilisateur récupéré avec succès : {}", profile);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération du profil utilisateur : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @Operation(summary = "Modifier le profil utilisateur")
    @PutMapping("/me")
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileDTO profileDTO) {
        try {
            userService.updateUserProfile(profileDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "S'abonner à un thème")
    @PostMapping("/topics/{topicId}")
    public ResponseEntity<?> subscribeToTopic(@PathVariable("topicId") Long topicId) {
        try {
            userService.subscribeToTopic(topicId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Se désabonner d'un thème")
    @DeleteMapping("/topics/{topicId}")
    public ResponseEntity<?> unsubscribeFromTopic(@PathVariable("topicId") Long topicId) {
        try {
            userService.unsubscribeFromTopic(topicId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @Operation(summary = "Récupérer un abonnement spécifique")
    @GetMapping("/subscriptions/{topicId}")
    public ResponseEntity<TopicDTO> getUserSubscriptionById(@PathVariable("topicId") Long topicId) {
        TopicDTO topicDTO = userService.getUserSubscriptionById(topicId);
        return ResponseEntity.ok(topicDTO);
    }








    @Operation(summary = "Afficher les abonnements de l'utilisateur")
    @GetMapping("/me/subscriptions")
    public ResponseEntity<List<TopicDTO>> getUserSubscriptions() {
        List<TopicDTO> subscriptions = userService.getUserSubscriptions()
                .stream()
                .map(topic -> new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(subscriptions);
    }



}
