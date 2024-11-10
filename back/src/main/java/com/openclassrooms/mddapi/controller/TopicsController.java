package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
@Tag(name = "TopicsController", description = "Gestion des thèmes")
public class TopicsController {

    private final TopicService topicService;

    public TopicsController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(summary = "Liste de tous les thèmes")
    @GetMapping
    public ResponseEntity<?> getAllTopics() {
        try {
            return ResponseEntity.ok(topicService.getAllTopics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
