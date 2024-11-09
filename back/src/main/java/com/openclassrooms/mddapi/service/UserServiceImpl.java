package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.projections.UserWithSubscriptions;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile() {
        User user = userRepository.findUserWithSubscriptionsByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        List<String> subscribedTopics = user.getSubscriptions().stream()
                .map(Topic::getName)
                .collect(Collectors.toList());

        return new UserProfileDTO(user.getUsername(), user.getEmail(), subscribedTopics);
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            logger.info("Email de l'utilisateur courant : {}", email);
            return email;
        } else {
            logger.warn("Utilisateur non authentifié lors de la récupération de l'email");
            throw new IllegalArgumentException("Utilisateur non authentifié");
        }
    }


    @Override
    public void updateUserProfile(UserProfileDTO profileDTO) {
        User user = getCurrentUser();
        user.setUsername(profileDTO.getUsername());
        user.setEmail(profileDTO.getEmail());
        userRepository.save(user);
    }

    @Override
    public void subscribeToTopic(Long topicId) {
        User user = getCurrentUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));
        user.getSubscriptions().add(topic);
        userRepository.save(user);
    }

    @Override
    public void unsubscribeFromTopic(Long topicId) {
        User user = getCurrentUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));
        user.getSubscriptions().remove(topic);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return findByEmail(email);
        } else {
            throw new IllegalArgumentException("Utilisateur non authentifié");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Topic> getUserSubscriptions() {
        User currentUser = getCurrentUser();
        return new ArrayList<>(currentUser.getSubscriptions());
    }

}
