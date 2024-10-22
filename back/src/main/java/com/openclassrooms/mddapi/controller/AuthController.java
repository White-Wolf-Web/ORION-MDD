package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.AuthLoginDto;
import com.openclassrooms.mddapi.dto.AuthRegisterDto;
import com.openclassrooms.mddapi.dto.JwtTokenDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.AuthService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    // Connexion
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginDto loginDto) {
        try {
            JwtTokenDto tokenDto = authService.login(loginDto);
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    // Inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRegisterDto registerDto) {
        try {
            authService.register(registerDto);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Récupérer les informations de l'utilisateur connecté
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();  // Le nom correspond à l'email dans ce cas

        UserDto user = userService.findUserByEmail(currentUserEmail);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
