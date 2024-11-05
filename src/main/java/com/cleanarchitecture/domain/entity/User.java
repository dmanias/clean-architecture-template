package com.cleanarchitecture.domain.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User extends BaseEntity {
    private final String email;
    private String name;
    private Status status;

    private User(String id, String email, String name) {
        super(id);
        this.email = email;
        this.name = name;
        this.status = Status.ACTIVE;
    }

    public static User create(String email, String name) {
        return new User(UUID.randomUUID().toString(), email, name);
    }

    public void deactivate() {
        this.status = Status.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public enum Status {
        ACTIVE, INACTIVE
    }
}
