package com.api.pharma.usecases;

import com.api.pharma.dto.AuthenticationResponse;
import com.api.pharma.dto.RegisterUserRequest;
import com.api.pharma.entities.User;
import com.api.pharma.enums.Role;
import com.api.pharma.ports.in.RegisterUserInputPort;
import com.api.pharma.ports.out.TokenRepositoryOutputPort;
import com.api.pharma.ports.out.TokenServiceOutputPort;
import com.api.pharma.ports.out.UserRepositoryOutputPort;
import com.api.pharma.valueobjects.Email;
import com.api.pharma.valueobjects.UserName;
import com.api.pharma.valueobjects.password.HashedPassword;
import com.api.pharma.valueobjects.password.Password;
import com.api.pharma.valueobjects.password.PasswordHasher;
import com.api.pharma.valueobjects.password.PasswordPolicy;

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

        userRepositoryOutputPort.findByEmail(request.email()).ifPresent(user -> {
            throw new IllegalArgumentException("Email already in use");
        });

        Email email = new Email(request.email());
        UserName userName = new UserName(request.username());
        Password password = new Password(request.password(), passwordPolicy);
        Role role = Role.valueOf(request.role().toUpperCase());

        HashedPassword hashedPassword = password.hashWith(passwordHasher);

        User user = User.selfRegistered(userName, email, hashedPassword, role);

        userRepositoryOutputPort.save(user);

        String jwtToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        tokenRepositoryOutputPort.saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }
}
