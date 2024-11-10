package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserProfileDTO toDTO(User user) {
        // Map each Topic to a TopicDTO, providing id, name, and description
        List<TopicDTO> subscribedTopics = user.getSubscriptions().stream()
                .map(topic -> new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()))
                .collect(Collectors.toList());
        return new UserProfileDTO(user.getUsername(), user.getEmail(), subscribedTopics);
    }

    public User toEntity(UserProfileDTO userProfileDTO, User user) {
        user.setUsername(userProfileDTO.getUsername());
        user.setEmail(userProfileDTO.getEmail());
        return user;
    }
}

