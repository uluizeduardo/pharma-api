package com.api.pharma.persistence.mapper;

import com.api.pharma.entities.Token;
import com.api.pharma.persistence.entity.TokenEntity;
import com.api.pharma.persistence.entity.UserEntity;
import com.api.pharma.valueobjects.TokenId;
import com.api.pharma.valueobjects.UserId;

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
                .build();
    }

    public static Token toDomain(TokenEntity tokenEntity) {
        return new Token(
                new TokenId(tokenEntity.getId()),
                tokenEntity.getToken(),
                tokenEntity.getTokenType(),
                tokenEntity.isRevoked(),
                tokenEntity.isExpired(),
                new UserId(tokenEntity.getUserEntity().getId())
        );
    }
}
