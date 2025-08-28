package com.api.pharma.modules.auth_module.application.usecases;

import com.api.pharma.modules.auth_module.application.dto.AuthenticationRequest;
import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;
import com.api.pharma.modules.auth_module.application.exceptions.UserException;
import com.api.pharma.modules.auth_module.application.ports.in.AuthenticateUserInputPort;
import com.api.pharma.modules.auth_module.application.ports.out.AuthenticationManagerOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenServiceOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.UserRepositoryOutputPort;

public class AuthenticateUserUseCase implements AuthenticateUserInputPort {

    private final UserRepositoryOutputPort userRepository;
    private final TokenRepositoryOutputPort tokenRepository;
    private final TokenServiceOutputPort tokenService;
    private final AuthenticationManagerOutputPort authenticationProvider;

    public AuthenticateUserUseCase(UserRepositoryOutputPort userRepository,
                                   TokenRepositoryOutputPort tokenRepository,
                                   TokenServiceOutputPort tokenService,
                                   AuthenticationManagerOutputPort authenticationProvider) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.authenticationProvider = authenticationProvider;
    }


    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationProvider.authenticate(request.email(), request.password());

        var user = userRepository.findByEmail(request.email())
                                 .orElseThrow(() -> UserException.userEmailNotFound(request.email()));
        var jwtToken = tokenService.generateAccessToken(user);
        var refreshToken = tokenService.generateRefreshToken(user);

        tokenRepository.revokeAllUserTokens(user);
        tokenRepository.saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }
}
