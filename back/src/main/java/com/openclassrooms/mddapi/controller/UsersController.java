package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "UsersController", description = "Gestion des utilisateurs et abonnements")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Consulter le profil utilisateur")
    @GetMapping("/me")
    public UserProfileDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("Utilisateur non authentifié");
        }

        // Utiliser l'email pour récupérer l'utilisateur
        User user = userService.findByEmail(userDetails.getUsername()); // getUsername() renvoie l'email dans ce contexte
        return new UserProfileDTO(user.getUsername(), user.getEmail());
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
    public ResponseEntity<?> subscribeToTopic(@PathVariable Long topicId) {
        try {
            userService.subscribeToTopic(topicId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Se désabonner d'un thème")
    @DeleteMapping("/topics/{topicId}")
    public ResponseEntity<?> unsubscribeFromTopic(@PathVariable Long topicId) {
        try {
            userService.unsubscribeFromTopic(topicId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}