package com.cleanarchitecture.application.port.output;

import com.cleanarchitecture.domain.entity.User;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}