package com.cleanarchitecture.application.service;

import com.cleanarchitecture.application.port.input.CreateUserUseCase;
import com.cleanarchitecture.application.port.output.EventPublisher;
import com.cleanarchitecture.application.port.output.UserRepository;
import com.cleanarchitecture.application.dto.CreateUserCommand;
import com.cleanarchitecture.application.dto.UserDto;
import com.cleanarchitecture.domain.entity.User;
import com.cleanarchitecture.domain.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UserService implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public UserDto createUser(CreateUserCommand command) {
        // Check if email is already taken
        userRepository.findByEmail(command.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        // Create and save user
        User user = User.create(command.getEmail(), command.getName());
        User savedUser = userRepository.save(user);

        // Publish event
        eventPublisher.publish(new UserCreatedEvent(savedUser));

        return UserDto.fromDomain(savedUser);
    }
}