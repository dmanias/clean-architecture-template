package com.cleanarchitecture.domain.event;

import lombok.Getter;
import com.cleanarchitecture.domain.entity.User;

@Getter
public class UserCreatedEvent extends DomainEvent {
    private final String userId;
    private final String email;

    public UserCreatedEvent(User user) {
        super();
        this.userId = user.getId();
        this.email = user.getEmail();
    }
}
