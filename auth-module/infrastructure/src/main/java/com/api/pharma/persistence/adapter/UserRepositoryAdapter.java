package com.api.pharma.persistence.adapter;

import com.api.pharma.entities.User;
import com.api.pharma.persistence.mapper.UserEntityMapper;
import com.api.pharma.persistence.repository.UserRepository;
import com.api.pharma.ports.out.UserRepositoryOutputPort;

import java.util.Optional;

public class UserRepositoryAdapter implements UserRepositoryOutputPort {

    private final UserRepository userRepository;

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        var userEntity = UserEntityMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntityMapper::toDomain);
    }
}
