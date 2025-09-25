package com.api.pharma.modules.auth_module.application.usecases;

import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;
import com.api.pharma.modules.auth_module.application.dto.RegisterUserRequest;
import com.api.pharma.modules.auth_module.application.exceptions.UserException;
import com.api.pharma.modules.auth_module.application.ports.in.RegisterUserInputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.TokenServiceOutputPort;
import com.api.pharma.modules.auth_module.application.ports.out.UserRepositoryOutputPort;
import com.api.pharma.modules.auth_module.domain.entities.User;
import com.api.pharma.modules.auth_module.domain.enums.Role;
import com.api.pharma.modules.auth_module.domain.valueobjects.Email;
import com.api.pharma.modules.auth_module.domain.valueobjects.UserName;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.HashedPassword;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.Password;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.PasswordHasher;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.PasswordPolicy;

public class RegisterUserUseCase implements RegisterUserInputPort {

    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final TokenRepositoryOutputPort tokenRepositoryOutputPort;
    private final TokenServiceOutputPort tokenService;
    private final PasswordHasher passwordHasher;
    private final PasswordPolicy passwordPolicy;

    public RegisterUserUseCase(UserRepositoryOutputPort userRepositoryOutputPort,
                               TokenRepositoryOutputPort tokenRepositoryOutputPort,
                               TokenServiceOutputPort tokenService,
                               PasswordHasher passwordHasher,
                               PasswordPolicy passwordPolicy) {
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.tokenRepositoryOutputPort = tokenRepositoryOutputPort;
        this.tokenService = tokenService;
        this.passwordHasher = passwordHasher;
        this.passwordPolicy = passwordPolicy;
    }

    @Override
    public AuthenticationResponse registerUser(RegisterUserRequest request) {

        System.out.print("Email da requisição: " + request.email());
        userRepositoryOutputPort.findByEmail(request.email()).ifPresent(user -> {
            throw UserException.emailAlreadyInUse(request.email());
        });

        Email email = new Email(request.email());
        UserName userName = new UserName(request.username());
        Password password = new Password(request.password(), passwordPolicy);
        Role role = Role.valueOf(request.role().toUpperCase());
        HashedPassword hashedPassword = password.hashWith(passwordHasher);

        User user = User.selfRegistered(userName, email, hashedPassword, role);

        User userSaved = userRepositoryOutputPort.save(user);

        String jwtToken = tokenService.generateAccessToken(userSaved);
        String refreshToken = tokenService.generateRefreshToken(userSaved);

        tokenRepositoryOutputPort.saveUserToken(userSaved, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }
}
