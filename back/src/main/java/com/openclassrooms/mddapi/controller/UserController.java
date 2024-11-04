package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    // Récupérer les informations de l'utilisateur connecté
    @GetMapping("/me")
    @Operation(summary = "Get current user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String currentUserEmail = authentication.getName(); // Récupère l'email de l'utilisateur connecté

        UserDto user = userService.findUserByEmail(currentUserEmail);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Mettre à jour le profil de l'utilisateur connecté
    @PutMapping("/me")
    @Operation(summary = "Update the current user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDto> updateUserProfile(@RequestBody UserDto userDto, Authentication authentication) {
        String currentUserEmail = authentication.getName();
        userDto.setEmail(currentUserEmail);
        UserDto updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/me/themes")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(Authentication authentication) {
        String currentUserEmail = authentication.getName();
        List<SubscriptionDto> subscriptions = userService.getUserSubscriptions(currentUserEmail);
        return ResponseEntity.ok(subscriptions);
    }

    // S'abonner à un thème pour l'utilisateur connecté
    @PostMapping("/me/themes/{themeId}")
    @Operation(summary = "Subscribe to a theme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully subscribed to the theme"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Theme not found")
    })
    public ResponseEntity<UserDto> subscribeToTheme(@PathVariable Long themeId, Authentication authentication) {
        String currentUserEmail = authentication.getName();
        UserDto updatedUser = userService.subscribeToTheme(themeId, currentUserEmail);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    // Se désabonner d'un thème pour l'utilisateur connecté
    @DeleteMapping("/me/themes/{themeId}")
    @Operation(summary = "Unsubscribe from a theme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unsubscribed from the theme"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Theme not found")
    })
    public ResponseEntity<UserDto> unsubscribeFromTheme(@PathVariable Long themeId, Authentication authentication) {
        String currentUserEmail = authentication.getName();
        UserDto updatedUser = userService.unsubscribeFromTheme(themeId, currentUserEmail);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


}
