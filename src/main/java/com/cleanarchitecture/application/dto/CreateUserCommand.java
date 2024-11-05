package com.cleanarchitecture.application.dto;

import lombok.Value;

@Value
public class CreateUserCommand {
    String email;
    String name;
}
