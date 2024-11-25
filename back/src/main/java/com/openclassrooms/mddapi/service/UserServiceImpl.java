package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.UserProfileDTO;
// import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
//import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder; // Injection de PasswordEncoder



    // Récupère le profil utilisateur et ses abonnements.
    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile() {

        // Récupère l'utilisateur et ses abonnements à partir de son email.
        User user = userRepository.findUserWithSubscriptionsByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        List<TopicDTO> subscriptions = user.getSubscriptions().stream()
                .map(topic -> new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()))
                .collect(Collectors.toList());

        return new UserProfileDTO(user.getUsername(), user.getEmail(), subscriptions);
    }


    //  Méthode pour obtenir l'email de l'utilisateur connecté.
    private String getCurrentUserEmail() {

        // Obtient l'objet principal (email ou UserDetails) de l'utilisateur authentifié.
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


    // On met à jour le profil utilisateur.
    @Override
    @Transactional
    public boolean updateUserProfile(UserProfileDTO profileDTO) {
        User user = getCurrentUser();
        boolean emailChanged = false;

        // Vérifier si le nom d'utilisateur doit être mis à jour
        if (!user.getUsername().equals(profileDTO.getUsername())) {
            if (userRepository.existsByUsername(profileDTO.getUsername())) {
                throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris par un autre utilisateur.");
            }
            user.setUsername(profileDTO.getUsername());
        }

        // Vérifier si l'email doit être mis à jour
        if (!user.getEmail().equals(profileDTO.getEmail())) {
            if (userRepository.existsByEmail(profileDTO.getEmail())) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur.");
            }
            user.setEmail(profileDTO.getEmail());
            emailChanged = true;
        }

        // Vérifier si le mot de passe doit être mis à jour
        if (profileDTO.getPassword() != null && !profileDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(profileDTO.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);

        return emailChanged;
    }


    // Abonne l'utilisateur connecté à un topic (thème) spécifique.
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


    // Désabonne l'utilisateur connecté d'un topic (thème) spécifique.
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


    // Récupère les détails d'un abonnement spécifique pour l'utilisateur connecté.
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


    // Recherche un utilisateur par email.
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }


    // Méthode pour obtenir l'utilisateur actuellement connecté.
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'email : " + currentUserEmail));
    }


    // Récupère tous les abonnements de l'utilisateur connecté.
    @Override
    @Transactional(readOnly = true)
    public List<Topic> getUserSubscriptions() {
        User currentUser = getCurrentUser(); // Obtient l'utilisateur actuellement connecté
        return topicRepository.findByUserSubscription(currentUser); // Utilise la méthode personnalisée pour obtenir les topics
    }


    //  Vérifie si un utilisateur est abonné à un topic spécifique.
    @Override
    public boolean isUserSubscribedToTopic(Long topicId) {
        Long userId = getCurrentUser().getId();
        return userRepository.isUserSubscribedToTopic(userId, topicId);
    }
}
