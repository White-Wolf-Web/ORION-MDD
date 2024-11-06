package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserLoginDTO;
import com.openclassrooms.mddapi.dto.UserRegistrationDTO;

public interface AuthService {
    void registerUser(UserRegistrationDTO registrationDTO);

    String loginUser(UserLoginDTO loginDTO);
}
