package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;


    // Récupère tous les topics depuis la base de données grâce au repository.
    @Override
    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topic -> new TopicDTO(
                        topic.getId(),
                        topic.getName(),
                        topic.getDescription()
                ))
                .collect(Collectors.toList());
    }
}
