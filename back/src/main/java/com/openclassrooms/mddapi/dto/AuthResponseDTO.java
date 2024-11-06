package com.openclassrooms.mddapi.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
}
