package com.api.pharma.modules.auth_module.application.usecases;

import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;
import com.api.pharma.modules.auth_module.application.exceptions.UserException;
import com.api.pharma.modules.auth_module.application.ports.in.RefreshTokenInputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenServiceOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.UserRepositoryOutputPort;
import com.api.pharma.modules.auth_module.domain.entities.User;

public class RefreshTokenUseCase implements RefreshTokenInputPort {

    private final TokenServiceOutputPort tokenService;
    private final TokenRepositoryOutputPort tokenRepository;
    private final UserRepositoryOutputPort userRepository;

    public RefreshTokenUseCase(TokenServiceOutputPort tokenService,
                               TokenRepositoryOutputPort tokenRepository,
                               UserRepositoryOutputPort userRepository) {
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {

        String email = tokenService.extractUsername(refreshToken);
        User user    = userRepository.findByEmail(email)
                                       .orElseThrow(() -> UserException.userEmailNotFound(email));

        boolean valid = tokenService.isTokenValid(refreshToken, user);
        if (!valid) {
           throw new IllegalArgumentException("Invalid refresh token");
        }

        String newAccessToken = tokenService.generateAccessToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        tokenRepository.saveUserToken(user, newAccessToken);
        tokenRepository.saveUserToken(user, newRefreshToken);

        return new AuthenticationResponse(newAccessToken, newRefreshToken);
    }
}
