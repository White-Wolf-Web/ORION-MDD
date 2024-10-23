package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscriptions", description = "Endpoints for managing subscriptions to topics")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // S'abonner à un thème
    @PostMapping("/subscribe")
    @Operation(summary = "Subscribe to a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully subscribed to the topic"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<SubscriptionDto> subscribeToTopic(@RequestBody SubscriptionDto subscriptionDto, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();

        SubscriptionDto subscription = subscriptionService.saveSubscription(subscriptionDto);  // Utiliser saveSubscription ici

        SubscriptionDto updatedSubscription = subscriptionService.subscribeToTopic(subscription, email);

        return new ResponseEntity<>(updatedSubscription, HttpStatus.CREATED);
    }



    // Se désabonner d'un thème
    @DeleteMapping("/unsubscribe/{id}")
    @Operation(summary = "Unsubscribe from a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully unsubscribed from the topic"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> unsubscribeFromTopic(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Operation(summary = "Get all subscriptions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscriptions"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        List<SubscriptionDto> subscriptions = subscriptionService.findAllSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the subscription"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDto subscriptionDto) {
        subscriptionDto.setId(id);
        SubscriptionDto updatedSubscription = subscriptionService.updateSubscription(subscriptionDto);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    // Récupérer les abonnements de l'utilisateur connecté
    @GetMapping("/my-subscriptions")
    @Operation(summary = "Get subscriptions for the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user subscriptions"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        List<SubscriptionDto> userSubscriptions = subscriptionService.findSubscriptionsByUser(email);
        return new ResponseEntity<>(userSubscriptions, HttpStatus.OK);
    }
}
