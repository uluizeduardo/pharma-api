package com.api.pharma.modules.auth_module.infrastrucure.persistence.mapper;

import com.api.pharma.modules.auth_module.domain.entities.Token;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.entity.TokenEntity;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.entity.UserEntity;
import com.api.pharma.modules.auth_module.domain.valueobjects.TokenId;
import com.api.pharma.modules.auth_module.domain.valueobjects.UserId;

public class TokenEntityMapper {

    public static TokenEntity toEntity(Token token) {
        var tokenState = token.exportState();

        var userEntity = new UserEntity();
        userEntity.setId(tokenState.userId());

        return TokenEntity.builder()
                .id(tokenState.id())
                .userEntity(userEntity)
                .token(tokenState.tokenValue())
                .tokenType(tokenState.tokenType())
                .expired(tokenState.expired())
                .revoked(tokenState.revoked())
                .createdAt(tokenState.createdAt())
                .build();
    }

    public static Token toDomain(TokenEntity tokenEntity) {
        return new Token(
                new TokenId(tokenEntity.getId()),
                tokenEntity.getToken(),
                tokenEntity.getTokenType(),
                tokenEntity.isRevoked(),
                tokenEntity.isExpired(),
                new UserId(tokenEntity.getUserEntity().getId()),
                tokenEntity.getCreatedAt()
        );
    }
}
