package com.cleanarchitecture.application.dto;

import lombok.Value;
import com.cleanarchitecture.domain.entity.User;

@Value
public class UserDto {
    String id;
    String email;
    String name;
    String status;

    public static UserDto fromDomain(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getStatus().name()
        );
    }
}

