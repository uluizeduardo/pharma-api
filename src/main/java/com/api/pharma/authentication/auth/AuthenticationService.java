package com.api.pharma.authentication.auth;

import com.api.pharma.authentication.config.JwtService;
import com.api.pharma.model.entity.Token;
import com.api.pharma.model.entity.User;
import com.api.pharma.model.enums.TokenType;
import com.api.pharma.model.exceptions.UserException;
import com.api.pharma.repository.TokenRepository;
import com.api.pharma.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service class for handling user authentication and token management.
 *
 * <p>This class is annotated with {@code @Service}, which indicates
 * that it is a Spring-managed service component. It provides core functionalities for:
 * <ul>
 *   <li>User registration with encrypted passwords.</li>
 *   <li>Authentication of user credentials using Spring Security.</li>
 *   <li>Generation and validation of JWT access and refresh tokens.</li>
 *   <li>Revocation and management of user tokens.</li>
 *   <li>Refreshing access tokens via a valid refresh token.</li>
 * </ul>
 *
 * <p>The class interacts with the following components:
 * <ul>
 *   <li>{@link UserRepository}: For performing database operations related to user entities.</li>
 *   <li>{@link TokenRepository}: For managing token persistence and retrieval.</li>
 *   <li>{@link PasswordEncoder}: For securely encoding and comparing user passwords.</li>
 *   <li>{@link JwtService}: For generating, validating, and extracting information from JWT tokens.</li>
 *   <li>{@link AuthenticationManager}: For authenticating user credentials during login.</li>
 * </ul>
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository        = userRepository;
        this.tokenRepository       = tokenRepository;
        this.passwordEncoder       = passwordEncoder;
        this.jwtService            = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user in the system.
     *
     * @param request the registration details including username, email, password, and role
     * @return an AuthenticationResponse containing a JWT token and refresh token
     */
    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .userName(request.userName())
                .email(request.email())
                .password(this.passwordEncoder.encode(request.password()))
                .isActive(request.isActive())
                .role(request.role())
                .build();

        var savedUser    = userRepository.save(user);
        var jwtToken     = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);
    }

    /**
     * Saves a user's JWT token to the database.
     *
     * @param user the user to associate the token with
     * @param jwtToken the token to save
     */
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param request the authentication details containing email and password
     * @return an AuthenticationResponse containing a JWT token and refresh token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw UserException.fromAuthenticationFailed(e);
        }

        var user         = userRepository.findByEmail(request.email())
                                         .orElseThrow(() -> UserException.userEmailNotFound(request.email()));
        var jwtToken     = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthenticationResponse(jwtToken, refreshToken);

    }

    /**
     * Revokes all valid tokens for a given user by marking them as expired and revoked.
     *
     * @param user the user whose tokens should be revoked
     */
    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Refreshes the user's access token using a valid refresh token.
     *
     * @param request the HTTP request containing the Authorization header with the refresh token
     * @param response the HTTP response to send the new tokens
     * @throws IOException if an error occurs while writing the response
     */
    public AuthenticationResponse refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new RuntimeException("Invalid or missing Authorization header");
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail != null){
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);;
                saveUserToken(user, accessToken);

                return new AuthenticationResponse(accessToken, refreshToken);

            }
        }
        throw new RuntimeException("Invalid refresh token");
    }
}
