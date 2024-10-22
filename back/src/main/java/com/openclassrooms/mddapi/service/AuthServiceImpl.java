package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.AuthLoginDto;
import com.openclassrooms.mddapi.dto.AuthRegisterDto;
import com.openclassrooms.mddapi.dto.JwtTokenDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void checkPassword() {
        String rawPassword = "strongpassword";  // Le mot de passe en clair
        String encodedPassword = "$2a$10$k1n8WOVJxqTP4QPL1Ii/UeYBXegNG7buYjVW8nJzp1/IqAwOJwJ0u";  // Ton mot de passe encodé

        boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);

        if (isMatch) {
            System.out.println("Le mot de passe correspond !");
        } else {
            System.out.println("Le mot de passe ne correspond pas !");
        }
    }
    @Override
    public JwtTokenDto login(AuthLoginDto loginDto) {
        // Chercher l'utilisateur par email
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // Comparer le mot de passe fourni (texte clair) avec celui encodé dans la base de données
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Créer un objet Authentication avec les informations utilisateur et les rôles (authorities)
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, new ArrayList<>());  // Si l'utilisateur a des rôles, tu peux les ajouter ici

        // Si les mots de passe correspondent, générer un token JWT
        String token = jwtTokenProvider.generateToken(authentication);  // Utiliser l'objet Authentication ici
        return new JwtTokenDto(token);
    }




    @Override
    public void register(AuthRegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);
    }
}
