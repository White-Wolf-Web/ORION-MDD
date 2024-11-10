package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private EntityManager entityManager;

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
            logger.info("Email de l'utilisateur authentifié : {}", email);
            return email;
        } else {
            logger.warn("Échec de la récupération de l'email : principal n'est pas UserDetails, valeur obtenue : {}", principal);
            throw new IllegalArgumentException("Utilisateur non authentifié");
        }
    }


    @Override
    @Transactional
    public boolean updateUserProfile(UserProfileDTO profileDTO) {
        User user = getCurrentUser();
        boolean emailChanged = false;

        // Vérifiez si l'email doit être mis à jour
        if (!user.getEmail().equals(profileDTO.getEmail())) {
            // Si l'email est déjà utilisé par un autre utilisateur
            if (userRepository.existsByEmail(profileDTO.getEmail())) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur.");
            }
            user.setEmail(profileDTO.getEmail());
            emailChanged = true;
        }

        // Mettre à jour d'autres champs de l'utilisateur
        userMapper.toEntity(profileDTO, user);
        userRepository.save(user);

        return emailChanged;
    }



    @Override
    public void subscribeToTopic(Long topicId) {
        User currentUser = getCurrentUser();
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Thème non trouvé"));

        if (!currentUser.getSubscriptions().contains(topic)) {
            currentUser.getSubscriptions().add(topic);
            userRepository.save(currentUser); // Assure la persistance de l'abonnement
        }
    }


    @Override
    @Transactional
    public void unsubscribeFromTopic(Long topicId) {
        User currentUser = getCurrentUser();

        // Appel direct à la méthode de suppression
        int rowsAffected = userRepository.removeUserSubscription(currentUser.getId(), topicId);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("L'utilisateur n'est pas abonné à ce thème.");
        }

        logger.info("Abonnement au thème {} supprimé pour l'utilisateur {}", topicId, currentUser.getEmail());
    }





    @Override
    @Transactional(readOnly = true)
    public TopicDTO getUserSubscriptionById(Long topicId) {
        User currentUser = getCurrentUser();
        Topic topic = topicRepository.findByUserSubscription(currentUser).stream()
                .filter(t -> t.getId().equals(topicId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Abonnement non trouvé pour l'utilisateur"));

        return new TopicDTO(topic.getId(), topic.getName(), topic.getDescription());
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
