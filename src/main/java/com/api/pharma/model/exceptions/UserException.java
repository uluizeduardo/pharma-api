package com.api.pharma.model.exceptions;

import com.api.pharma.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;
import java.util.function.Supplier;

public class UserException extends AlertException {

    private static final Map<Class <? extends AuthenticationException>, Supplier<AlertException>> AUTHENTICATION_EXCEPTIONS =
            Map.of(
                    DisabledException.class, UserException::accountDisabled,
                    LockedException.class, UserException::accountBlocked,
                    BadCredentialsException.class, UserException::badCredentials
            );
    private static final String USER_NOT_FOUND  = "User not found!";
    private static final String USER_NOT_FOUND_BY_EMAIL = "User not found to email: ";
    private static final String ACCOUNT_DISABLED = "User account is disabled";
    private static final String ACCOUNT_LOCKED = "User account is blocked";
    private static final String INVALID_CREDENTIALS = "Bad credentials, Incorrect email or password.";
    private static final String AUTHENTICATION_FAILED = "Authentication failed";

    public UserException(String code, String message, HttpStatus httpStatus) {
        super(code, message, httpStatus);
    }

    public static AlertException userNotFound() {
        return new UserException("404", USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    public static AlertException userEmailNotFound(String email) {
        return new UserException("404", USER_NOT_FOUND_BY_EMAIL + email, HttpStatus.NOT_FOUND);
    }

    public static AlertException accountDisabled() {
        return new UserException("403", ACCOUNT_DISABLED, HttpStatus.FORBIDDEN);
    }

    public static AlertException accountBlocked() {
        return new UserException("403", ACCOUNT_LOCKED, HttpStatus.FORBIDDEN);
    }

    public static AlertException badCredentials() {
        return new UserException("401", INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }

    public static AlertException authenticationFailed() {
        return new UserException("401", AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED);
    }

    public static AlertException fromAuthenticationFailed(AuthenticationException e) {
        return AUTHENTICATION_EXCEPTIONS.getOrDefault(e.getClass(), UserException::authenticationFailed).get();
    }
}
