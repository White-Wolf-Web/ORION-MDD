package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // S'abonner à un thème
    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionDto> subscribeToTopic(@RequestBody SubscriptionDto subscriptionDto, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        SubscriptionDto subscription = subscriptionService.subscribeToTopic(subscriptionDto, email);
        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }


    // Se désabonner d'un thème
    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<Void> unsubscribeFromTopic(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
