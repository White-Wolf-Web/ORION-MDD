package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicCreateDto;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.service.TopicService;
import com.openclassrooms.mddapi.service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;
    private final UserService userService;

    public TopicController(TopicService topicService, UserService userService) {
        this.topicService = topicService;
        this.userService = userService;
    }


    // Liste de tous les topics
    @GetMapping
    public ResponseEntity<List<TopicDto>> findAllTopics() {
        List<TopicDto> topics = topicService.findAllTopics();
        return ResponseEntity.ok(topics);
    }

// S'abonner à un Topic
@PostMapping("/subscribe/{topicId}")
public ResponseEntity<String> subscribeToTopic(@PathVariable("topicId") Long topicId, Authentication authentication) {
    try {
        String userEmail = authentication.getName();
        Long userId = userService.findByEmail(userEmail).getId();
        topicService.subscribeToTopic(userId, topicId);
        return ResponseEntity.ok("Successfully subscribed to topic with ID: " + topicId);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Topic not found: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while subscribing to the topic: " + e.getMessage());
    }
}

// Se désabonner d'un Topic
@PostMapping("/unsubscribe/{topicId}")
public ResponseEntity<String> unsubscribeFromTopic(@PathVariable("topicId") Long topicId, Authentication authentication) {
    try {
        String userEmail = authentication.getName();
        Long userId = userService.findByEmail(userEmail).getId();
        topicService.unsubscribeFromTopic(userId, topicId);
        return ResponseEntity.ok("Successfully unsubscribed from topic with ID: " + topicId);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Topic not found: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unsubscribing from the topic: " + e.getMessage());
    }
}
}
