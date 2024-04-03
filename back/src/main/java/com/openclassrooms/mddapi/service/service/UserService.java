package com.openclassrooms.mddapi.service.service;

import com.openclassrooms.mddapi.dto.*;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface UserService {
    UserDto registerUser(AuthRegisterDto authRegisterDto) throws IOException;

    UserDto findUserById(Long id);
    UserDto findByEmail(String email);

    UserDto getCurrentUser(Authentication authentication) throws IOException;

    AuthResponseDto loginUser(AuthLoginDto authLoginDto);

    UserDto updateUserById(Long id, UserUpdateDto userUpdateDto);


    void deleteUserById(Long id);

}

