package com.openclassrooms.mddapi.service.service;

import com.openclassrooms.mddapi.dto.TopicDto;

import java.util.List;

public interface TopicService {

    List<TopicDto> findAllTopics();
    void subscribeToTopic(Long userId, Long topicId);
    void unsubscribeFromTopic(Long userId, Long topicId);
    List<TopicDto> findSubscribedTopicsByUserId(Long userId);
}
