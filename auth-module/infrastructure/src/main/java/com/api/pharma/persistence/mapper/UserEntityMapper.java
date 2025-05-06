package com.api.pharma.persistence.mapper;

import com.api.pharma.entities.User;
import com.api.pharma.persistence.entity.UserEntity;
import com.api.pharma.valueobjects.AuditInfo;
import com.api.pharma.valueobjects.Email;
import com.api.pharma.valueobjects.UserId;
import com.api.pharma.valueobjects.UserName;
import com.api.pharma.valueobjects.password.HashedPassword;

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
                        new UserId(userEntity.getCreatedBy())
                )
        );
    }

    public static UserEntity toEntity(User user) {
        var userState = user.exportState();
        return UserEntity.builder()
                .id(userState.id())
                .userName(userState.userName())
                .email(userState.email())
                .role(userState.role())
                .status(userState.status())
                .createdAt(userState.createdAt())
                .createdBy(userState.createdBy())
                .build();
    }
}
