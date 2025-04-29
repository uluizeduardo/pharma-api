package com.api.pharma.auth.ports.out;

import com.api.pharma.auth.entities.Token;
import com.api.pharma.auth.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepositoryOutputPort {
    void saveUserToken(User user, String jwtToken);
    List<Token> findAllValidTokenByUser(UUID userId);
    Optional<Token> findByToken(String token);
}
