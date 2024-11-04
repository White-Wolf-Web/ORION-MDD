package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto saveUser(UserDto userDto);  // Le type de retour doit être UserDto

    UserDto updateUser(UserDto userDto);  // Mettre à jour avec un UserDto en paramètre

    UserDto findUserById(Long id);  // Retourner UserDto

    List<UserDto> findAllUsers();  // Retourner une liste de UserDto

    UserDto findUserByEmail(String email);  // Trouver par email et retourner UserDto

    void deleteUser(Long id);  // Supprimer par ID

    UserDto subscribeToTheme(Long themeId, String email);

    UserDto unsubscribeFromTheme(Long themeId, String email);

    boolean isUserSubscribedToTheme(Long themeId, String email);

    List<SubscriptionDto> getUserSubscriptions(String email); // Récupérer les abonnements d'un utilisateur par email
}
