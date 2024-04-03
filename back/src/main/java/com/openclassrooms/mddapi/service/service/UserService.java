package com.openclassrooms.mddapi.service.service;

import com.openclassrooms.mddapi.dto.AuthLoginDto;
import com.openclassrooms.mddapi.dto.AuthRegisterDto;
import com.openclassrooms.mddapi.dto.AuthResponseDto;
import com.openclassrooms.mddapi.dto.UserDto;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface UserService {
    UserDto registerUser(AuthRegisterDto authRegisterDto) throws IOException;

    UserDto findUserById(Long id);

    UserDto getCurrentUser(Authentication authentication) throws IOException;

    AuthResponseDto loginUser(AuthLoginDto authLoginDto);
}

