package com.api.pharma.modules.auth_module.infrastrucure.security.token;

import com.api.pharma.modules.auth_module.application.ports.out.TokenServiceOutputPort;
import com.api.pharma.modules.auth_module.domain.entities.User;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.mapper.UserEntityMapper;
import com.api.pharma.modules.auth_module.infrastrucure.security.user.UserDetailsImpl;
import org.springframework.stereotype.Service;

@Service
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
