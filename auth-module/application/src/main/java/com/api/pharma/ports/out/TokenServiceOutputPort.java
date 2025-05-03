package com.api.pharma.ports.out;

import com.api.pharma.entities.User;

public interface TokenServiceOutputPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    boolean isTokenValid(String token, User user);
    String extractUsername(String token);
}
