package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegistrationDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;  // Injection de JwtTokenUtil

    @Override
    public void registerUser(UserRegistrationDTO registrationDTO) {

        // Vérifie si l'email est déjà utilisé par un autre utilisateur
        if (userRepository.existsByEmail(registrationDTO.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }
        // Vérifie si le nom d'utilisateur est déjà pris
        if (userRepository.existsByUsername(registrationDTO.getUsername())) {
            throw new IllegalArgumentException("Ce nom d'utilisateur est déjà pris");
        }

        // Crée un nouvel utilisateur avec les données du formulaire d'inscription
        User user = new User();
        user.setEmail(registrationDTO.getEmail());
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        userRepository.save(user);   // Sauvegarde l'utilisateur dans la base de données
    }

    @Override
    public String loginUser(UserLoginDTO loginDTO) {

        // Récupère l'utilisateur par email ou nom d'utilisateur
        User user = userRepository.findByEmail(loginDTO.getIdentifier())
                .orElseGet(() -> userRepository.findByUsername(loginDTO.getIdentifier())
                        .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé")));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }

        // Générer un token JWT avec l'email et le nom d'utilisateur
        return jwtTokenUtil.generateToken(user.getEmail(), user.getUsername());
    }

}