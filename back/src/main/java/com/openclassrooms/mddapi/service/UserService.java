package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

import java.util.List;


public interface UserService {
    UserProfileDTO getUserProfile();
    boolean updateUserProfile(UserProfileDTO profileDTO);

    void subscribeToTopic(Long topicId);
    void unsubscribeFromTopic(Long topicId);
    User findByEmail(String email);

    boolean isUserSubscribedToTopic(Long topicId);

    TopicDTO getUserSubscriptionById(Long topicId);

    List<Topic> getUserSubscriptions();

}
