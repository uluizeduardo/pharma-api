package com.api.pharma.modules.auth_module.application.ports.out;


import com.api.pharma.modules.auth_module.domain.entities.User;

public interface TokenServiceOutputPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    boolean isTokenValid(String token, User user);
    String extractUsername(String token);
}
