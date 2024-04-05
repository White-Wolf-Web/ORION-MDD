package com.openclassrooms.mddapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionDto {
    private Long id;
    private UserDto user;
    private TopicDto topic;

}