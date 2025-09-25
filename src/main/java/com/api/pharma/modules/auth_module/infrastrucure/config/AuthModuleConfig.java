package com.api.pharma.modules.auth_module.infrastrucure.config;

import com.api.pharma.modules.auth_module.application.ports.in.AuthenticateUserInputPort;
import com.api.pharma.modules.auth_module.application.ports.in.RefreshTokenInputPort;
import com.api.pharma.modules.auth_module.application.ports.in.RegisterUserInputPort;
import com.api.pharma.modules.auth_module.application.ports.out.AuthenticationManagerOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenServiceOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.UserRepositoryOutputPort;
import com.api.pharma.modules.auth_module.application.usecases.AuthenticateUserUseCase;
import com.api.pharma.modules.auth_module.application.usecases.RefreshTokenUseCase;
import com.api.pharma.modules.auth_module.application.usecases.RegisterUserUseCase;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.PasswordHasher;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.PasswordPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthModuleConfig {

    @Bean
    public RegisterUserInputPort registerUserUseCase(
            UserRepositoryOutputPort userRepository,
            TokenRepositoryOutputPort tokenRepository,
            TokenServiceOutputPort tokenService,
            PasswordHasher passwordHasher,
            PasswordPolicy passwordPolicy
    ) {
        return new RegisterUserUseCase(userRepository, tokenRepository, tokenService, passwordHasher, passwordPolicy);
    }

    @Bean
    public RefreshTokenInputPort refreshTokenUseCase(
            TokenServiceOutputPort tokenService,
            TokenRepositoryOutputPort tokenRepository,
            UserRepositoryOutputPort userRepository
    ) {
        return new RefreshTokenUseCase(tokenService, tokenRepository, userRepository);
    }

    @Bean
    public AuthenticateUserInputPort authenticateUserUseCase(
            UserRepositoryOutputPort userRepository,
            TokenRepositoryOutputPort tokenRepository,
            TokenServiceOutputPort tokenService,
            AuthenticationManagerOutputPort authManager
    ) {
        return new AuthenticateUserUseCase(userRepository, tokenRepository, tokenService, authManager);
    }
}

