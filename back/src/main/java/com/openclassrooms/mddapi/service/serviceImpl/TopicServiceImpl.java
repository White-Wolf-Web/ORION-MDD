package com.openclassrooms.mddapi.service.serviceImpl;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.service.TopicService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper modelMapper;

    public TopicServiceImpl(TopicRepository topicRepository, UserRepository userRepository, SubscriptionRepository subscriptionRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TopicDto> findAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicDto> topicDtos = topics.stream().map(topic -> modelMapper.map(topic, TopicDto.class)).collect(Collectors.toList());
        //  topicDtos.forEach(topicDto -> System.out.println(topicDto)); // Logger pour le débogage
        topicDtos.forEach(topicDto -> System.out.println("TopicDto: " + topicDto.getTitle() + ", " + topicDto.getContent()));


        return topicDtos;
    }


    @Override
    public void subscribeToTopic(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Sujet non trouvé"));

        // Vérifier si l'abonnement existe déjà
        boolean subscriptionExists = subscriptionRepository.existsByUserAndTopic(user, topic);
        if (!subscriptionExists) {
            // Créer un nouvel abonnement si non existant
            Subscription subscription = new Subscription();
            subscription.setUser(user);
            subscription.setTopic(topic);
            subscription.setCreated_at(new Date());
            subscription.setUpdated_at(new Date());
            subscriptionRepository.save(subscription);
        }
    }


    @Override
    public void unsubscribeFromTopic(Long userId, Long topicId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("Sujet non trouvé"));

        // Trouver l'abonnement à supprimer
        Subscription subscription = subscriptionRepository.findByUserAndTopic(user, topic).orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé"));
        subscriptionRepository.delete(subscription);
    }


}
