package com.api.pharma.persistence.adapter;

import com.api.pharma.entities.Token;
import com.api.pharma.entities.User;
import com.api.pharma.enums.TokenType;
import com.api.pharma.persistence.entity.TokenEntity;
import com.api.pharma.persistence.mapper.TokenEntityMapper;
import com.api.pharma.persistence.mapper.UserEntityMapper;
import com.api.pharma.persistence.repository.TokenRepository;
import com.api.pharma.ports.out.TokenRepositoryOutputPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TokenRepositoryAdapter implements TokenRepositoryOutputPort {

    private final TokenRepository tokenRepository;

    public TokenRepositoryAdapter(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var userEntity = UserEntityMapper.toEntity(user);
        var token = TokenEntity.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var userEntity = UserEntityMapper.toEntity(user);
        var validUserTokens = tokenRepository.findAllValidTokenByUser(userEntity.getId());

        if ( validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public List<Token> findAllValidTokenByUser(UUID userId) {
        List<TokenEntity> listToken = tokenRepository.findAllValidTokenByUser(userId);
        if (!listToken.isEmpty()) {
            return listToken.stream()
                    .map(TokenEntityMapper::toDomain)
                    .toList();
        }
        return null;
    }

    @Override
    public Optional<Token> findByToken(String token) {
        var tokenEntity = tokenRepository.findByToken(token);
        return tokenEntity.map(TokenEntityMapper::toDomain);
    }
}
