package com.cleanarchitecture.domain.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String userId) {
        super(String.format("User with ID %s not found", userId));
    }
}