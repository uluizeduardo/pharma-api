package com.api.pharma.modules.auth_module.application.ports.out;

import com.api.pharma.modules.auth_module.domain.entities.User;

import java.util.Optional;

public interface UserRepositoryOutputPort {
    User save(User user);
    Optional<User> findByEmail(String email);
}
