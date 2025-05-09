package com.api.pharma.usecases;

import com.api.pharma.dto.AuthenticationRequest;
import com.api.pharma.dto.AuthenticationResponse;
import com.api.pharma.ports.in.AuthenticateUserInputPort;
import com.api.pharma.ports.out.AuthenticationManagerOutputPort;
import com.api.pharma.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.ports.out.TokenServiceOutputPort;
import com.api.pharma.ports.out.UserRepositoryOutputPort;

public class AuthenticateUserUserUseCase implements AuthenticateUserInputPort {

    private final UserRepositoryOutputPort userRepository;
    private final TokenRepositoryOutputPort tokenRepository;
    private final TokenServiceOutputPort tokenService;
    private final AuthenticationManagerOutputPort authenticationProvider;

    public AuthenticateUserUserUseCase(UserRepositoryOutputPort userRepository, TokenRepositoryOutputPort tokenRepository, TokenServiceOutputPort tokenService, AuthenticationManagerOutputPort authenticationProvider) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationProvider.authenticate(request.email(), request.password());

        var user = userRepository.findByEmail(request.email())
                                 .orElseThrow(() -> new IllegalArgumentException("User not found"));
        var jwtToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        tokenRepository.revokeAllUserTokens(user);
        tokenRepository.saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }
}
