package com.api.pharma.security.token;

import com.api.pharma.entities.User;
import com.api.pharma.persistence.mapper.UserEntityMapper;
import com.api.pharma.ports.out.TokenServiceOutputPort;
import com.api.pharma.security.user.UserDetailsImpl;

public class JwtTokenService implements TokenServiceOutputPort {

    private final JwtProvider jwtProvider;

    public JwtTokenService(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String generateAccessToken(User user) {
        var userEntity = UserEntityMapper.toEntity(user);
        var userDetails = new UserDetailsImpl(userEntity);
        return jwtProvider.generateToken(userDetails);
    }

    @Override
    public String generateRefreshToken(User user) {
        var userEntity = UserEntityMapper.toEntity(user);
        var userDetails = new UserDetailsImpl(userEntity);
        return jwtProvider.generateRefreshToken(userDetails);
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        var userEntity = UserEntityMapper.toEntity(user);
        var userDetails = new UserDetailsImpl(userEntity);
        return jwtProvider.isTokenValid(token, userDetails);
    }

    @Override
    public String extractUsername(String token) {
        return jwtProvider.extractUsername(token);
    }
}
