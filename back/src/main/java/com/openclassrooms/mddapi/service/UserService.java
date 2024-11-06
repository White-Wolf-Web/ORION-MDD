package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.User;


public interface UserService {
    UserProfileDTO getUserProfile();
    void updateUserProfile(UserProfileDTO profileDTO);
    void subscribeToTopic(Long topicId);
    void unsubscribeFromTopic(Long topicId);

    User findByUsername(String username);
}
