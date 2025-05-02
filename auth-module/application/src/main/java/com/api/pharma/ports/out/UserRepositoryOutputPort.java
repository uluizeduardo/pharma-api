package com.api.pharma.ports.out;

import com.api.pharma.entities.User;

import java.util.Optional;

public interface UserRepositoryOutputPort {
    void save(User user);
    Optional<User> findByEmail(String email);
}
