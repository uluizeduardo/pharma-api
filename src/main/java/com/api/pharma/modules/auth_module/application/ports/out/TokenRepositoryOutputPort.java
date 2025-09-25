package com.api.pharma.modules.auth_module.application.ports.out;


import com.api.pharma.modules.auth_module.domain.entities.Token;
import com.api.pharma.modules.auth_module.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepositoryOutputPort {
    void saveUserToken(User user, String jwtToken);
    void revokeAllUserTokens(User user);
    List<Token> findAllValidTokenByUser(UUID userId);
    Optional<Token> findByToken(String token);
}
