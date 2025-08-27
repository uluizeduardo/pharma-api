package com.api.pharma.modules.auth_module.infrastrucure.persistence.adapter;

import com.api.pharma.modules.auth_module.domain.entities.User;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.exceptions.UserException;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.mapper.UserEntityMapper;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.repository.UserRepository;
import com.api.pharma.modules.auth_module.application.ports.out.UserRepositoryOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
public class UserRepositoryAdapter implements UserRepositoryOutputPort {

    private final UserRepository userRepository;

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User save(User user) {
        var userEntity = UserEntityMapper.toNewEntity(user);
        try{
            var savedUserEntity = userRepository.save(userEntity);

            if (savedUserEntity.getCreatedBy() == null) {
                savedUserEntity.setCreatedBy(savedUserEntity.getId());
                userRepository.save(savedUserEntity);
            }

            log.info("User saving with ID: {}", userEntity.getId());
            return UserEntityMapper.toDomain(savedUserEntity);
        } catch (Exception e) {
            log.error("Error to create user: {}", e.getMessage());
            log.error("Error to create user: {}", e.getStackTrace());
            log.error("User entity: {}", userEntity);
            throw UserException.errorCreatingUser();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntityMapper::toDomain);
    }
}
