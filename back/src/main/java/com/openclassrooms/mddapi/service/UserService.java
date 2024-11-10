package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

import java.util.List;


public interface UserService {
    UserProfileDTO getUserProfile();
    void updateUserProfile(UserProfileDTO profileDTO);
    void subscribeToTopic(Long topicId);
    void unsubscribeFromTopic(Long topicId);
    User findByEmail(String email);

    TopicDTO getUserSubscriptionById(Long topicId);

    List<Topic> getUserSubscriptions();

}
