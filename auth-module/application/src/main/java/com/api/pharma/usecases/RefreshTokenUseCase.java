package com.api.pharma.usecases;

import com.api.pharma.dto.AuthenticationResponse;
import com.api.pharma.entities.User;
import com.api.pharma.ports.in.RefreshTokenInputPort;
import com.api.pharma.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.ports.out.TokenServiceOutputPort;
import com.api.pharma.ports.out.UserRepositoryOutputPort;

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
                                       .orElseThrow(() -> new IllegalArgumentException("User not found"));

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
