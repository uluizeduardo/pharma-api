package com.api.pharma.modules.auth_module.infrastrucure.persistence.mapper;

import com.api.pharma.modules.auth_module.domain.entities.User;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.entity.UserEntity;
import com.api.pharma.modules.auth_module.domain.valueobjects.AuditInfo;
import com.api.pharma.modules.auth_module.domain.valueobjects.Email;
import com.api.pharma.modules.auth_module.domain.valueobjects.UserId;
import com.api.pharma.modules.auth_module.domain.valueobjects.UserName;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.HashedPassword;

public class UserEntityMapper {

    // Private constructor to hide the implicit public one
    private UserEntityMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static User toDomain(UserEntity userEntity) {

        return new User(
                new UserId(userEntity.getId()),
                new UserName(userEntity.getUserName()),
                new Email(userEntity.getEmail()),
                new HashedPassword(userEntity.getPassword()),
                userEntity.getRole(),
                userEntity.getStatus(),
                new AuditInfo(
                        userEntity.getCreatedAt(),
                        userEntity.getUpdatedAt(),
                        userEntity.getCreatedBy() != null ? new UserId(userEntity.getCreatedBy()) : null,
                        userEntity.getUpdatedBy() != null ? new UserId(userEntity.getUpdatedBy()) : null
                )
        );
    }

    public static UserEntity toNewEntity(User user) {
        var userState = user.exportNewUserState();
        return UserEntity.builder()
                .userName(userState.userName())
                .email(userState.email())
                .password(userState.password())
                .role(userState.role())
                .status(userState.status())
                .createdAt(userState.createdAt())
                .createdBy(userState.createdBy())
                .build();
    }

    public static UserEntity toEntity(User user) {
        var userState = user.exportState();
        return UserEntity.builder()
                .id(userState.id())
                .userName(userState.userName())
                .email(userState.email())
                .password(userState.password())
                .role(userState.role())
                .status(userState.status())
                .createdAt(userState.createdAt())
                .createdBy(userState.createdBy())
                .build();
    }
}
