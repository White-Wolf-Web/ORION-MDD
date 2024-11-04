package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = convertToUser(userDto);
        return convertToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = convertToUser(userDto);
        return convertToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user == null ? null : convertToUserDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return convertToUserDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<SubscriptionDto> getUserSubscriptions(String email) {
        User user = userRepository.findUserWithSubscriptionsByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return user.getSubscriptions().stream()
                .map(this::convertToSubscriptionDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UserDto subscribeToTheme(Long themeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Subscription subscription = subscriptionRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found with id: " + themeId));

        System.out.println("Utilisateur trouvé : " + user.getUsername());
        System.out.println("Thème trouvé : " + subscription.getName());

        if (!user.getSubscriptions().contains(subscription)) {
            user.getSubscriptions().add(subscription);
            user = userRepository.save(user);
            System.out.println("Souscription ajoutée avec succès !");
        } else {
            System.out.println("L'utilisateur est déjà souscrit à ce thème.");
        }

        return convertToUserDto(user);
    }




    @Override
    public UserDto unsubscribeFromTheme(Long themeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        Subscription subscription = subscriptionRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found with id: " + themeId));

        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
        return convertToUserDto(user);
    }

    @Override
    public boolean isUserSubscribedToTheme(Long themeId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Vérifier si le user a souscrit à un thème spécifique
        return user.getSubscriptions().stream()
                .anyMatch(subscription -> subscription.getId().equals(themeId));
    }


    // Méthode de conversion User en UserDto
    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreatedAt()));
        return userDto;
    }

    // Méthode de conversion UserDto en User
    private User convertToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }

    // Méthode de conversion Subscription en SubscriptionDto
    private SubscriptionDto convertToSubscriptionDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setName(subscription.getName());
        subscriptionDto.setDescription(subscription.getDescription());
        return subscriptionDto;
    }
}
