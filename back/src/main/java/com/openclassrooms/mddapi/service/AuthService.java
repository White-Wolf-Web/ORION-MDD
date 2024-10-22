package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.dto.AuthLoginDto;
import com.openclassrooms.mddapi.dto.AuthRegisterDto;
import com.openclassrooms.mddapi.dto.JwtTokenDto;

public interface AuthService {

    JwtTokenDto login(AuthLoginDto loginDto);

    void register(AuthRegisterDto registerDto);
}
