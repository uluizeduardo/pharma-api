package com.api.pharma.auth.ports.out;

import com.api.pharma.auth.entities.User;

import java.util.Optional;

public interface UserRepositoryOutputPort {
    void save(User user);
    Optional<User> findByEmail(String email);
}
