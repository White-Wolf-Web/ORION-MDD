package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
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

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile() {
        User user = userRepository.findUserWithSubscriptionsByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        List<TopicDTO> subscriptions = user.getSubscriptions().stream()
                .map(topic -> new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()))
                .collect(Collectors.toList());

        return new UserProfileDTO(user.getUsername(), user.getEmail(), subscriptions);
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
        userMapper.toEntity(profileDTO, user);
        userRepository.save(user);
    }

    @Override
    public void subscribeToTopic(Long topicId) {
        User currentUser = getCurrentUser(); // Récupère l'utilisateur connecté
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        if (!currentUser.getSubscriptions().contains(topic)) {
            currentUser.getSubscriptions().add(topic);
            userRepository.save(currentUser); // Assure la persistance de l'abonnement
        }
    }



    @Override
    public void unsubscribeFromTopic(Long topicId) {
        User user = getCurrentUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        if (user.getSubscriptions().contains(topic)) {
            user.getSubscriptions().remove(topic);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("L'utilisateur n'est pas abonné à ce thème");
        }
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
        User currentUser = getCurrentUser(); // Obtient l'utilisateur actuellement connecté
        return topicRepository.findByUserSubscription(currentUser); // Utilise la méthode personnalisée pour obtenir les topics
    }


}
