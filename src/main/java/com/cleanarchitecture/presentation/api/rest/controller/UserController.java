package com.cleanarchitecture.presentation.api.rest.controller;

import com.cleanarchitecture.application.dto.CreateUserCommand;
import com.cleanarchitecture.application.dto.UserDto;
import com.cleanarchitecture.application.port.input.CreateUserUseCase;
import com.cleanarchitecture.presentation.api.rest.request.CreateUserRequest;
import com.cleanarchitecture.presentation.api.rest.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        CreateUserCommand command = new CreateUserCommand(
                request.getEmail(),
                request.getName()
        );

        UserDto createdUser = createUserUseCase.createUser(command);

        return new ResponseEntity<>(
                ApiResponse.success(createdUser, "User created successfully"),
                HttpStatus.CREATED
        );
    }
}
