package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.service.service.TopicService;
import com.openclassrooms.mddapi.service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> subscribeToTopic(@PathVariable("topicId") Long topicId, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            String userEmail = authentication.getName();
            Long userId = userService.findByEmail(userEmail).getId();
            topicService.subscribeToTopic(userId, topicId);
            response.put("message", "Successfully subscribed to topic with ID: " + topicId);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "User or Topic not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "An error occurred while subscribing to the topic: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Se désabonner d'un Topic
    @PostMapping("/unsubscribe/{topicId}")
    public ResponseEntity<Map<String, Object>> unsubscribeFromTopic(@PathVariable("topicId") Long topicId, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            String userEmail = authentication.getName();
            Long userId = userService.findByEmail(userEmail).getId();
            topicService.unsubscribeFromTopic(userId, topicId);
            response.put("message", "Successfully unsubscribed to topic with ID: " + topicId);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.put("error", "User or Topic not found: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "An error occurred while subscribing to the topic: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/me/subscribed")
    public ResponseEntity<List<TopicDto>> getSubscribedTopics(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            Long userId = userService.findByEmail(userEmail).getId();
            List<TopicDto> subscribedTopics = topicService.findSubscribedTopicsByUserId(userId);
            return ResponseEntity.ok(subscribedTopics);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


