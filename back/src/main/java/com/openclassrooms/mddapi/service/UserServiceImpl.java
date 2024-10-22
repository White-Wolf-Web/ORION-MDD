package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = convertToUser(userDto);                  // Convertir UserDto en User
        return convertToUserDto(userRepository.save(user));  // Sauvegarder et retourner UserDto
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        // Conversion du DTO en User et enregistrement
        User user = convertToUser(userDto);
        return convertToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return convertToUserDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
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

    // Méthode pour convertir User en UserDto
    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreatedAt()));
        return userDto;
    }

    // Méthode pour convertir UserDto en User (si besoin)
    private User convertToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        // Assurez-vous d'inclure d'autres propriétés ici si nécessaire
        return user;
    }
}