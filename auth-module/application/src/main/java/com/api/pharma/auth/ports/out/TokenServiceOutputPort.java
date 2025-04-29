package com.api.pharma.auth.ports.out;

import com.api.pharma.auth.entities.User;

public interface TokenServiceOutputPort {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
}
