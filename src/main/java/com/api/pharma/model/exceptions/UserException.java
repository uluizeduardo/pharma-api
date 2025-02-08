package com.api.pharma.model.exceptions;

import com.api.pharma.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class UserException extends AlertException {

    private static final String USER_NOT_FOUND  = "User not found!";
    private static final String USER_NOT_FOUND_BY_EMAIL = "User not found to email: ";
    private static final String ACCOUNT_DISABLED = "User account is disabled";
    private static final String ACCOUNT_LOCKED = "User account is blocked";
    private static final String INVALID_CREDENTIALS = "Bad credentials, Incorrect email or password.";
    private static final String AUTHENTICATION_FAILED = "Authentication failed";

    public UserException(String code, String message, HttpStatus httpStatus) {
        super(code, message, httpStatus);
    }

    public static AlertException UserNotFound() {
        return new UserException("404", USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    public static AlertException UserEmailNotFound(String email) {
        return new UserException("404", USER_NOT_FOUND_BY_EMAIL + email, HttpStatus.NOT_FOUND);
    }

    public static AlertException AccountDisabled() {
        return new UserException("403", ACCOUNT_DISABLED, HttpStatus.FORBIDDEN);
    }

    public static AlertException AccountBlocked() {
        return new UserException("403", ACCOUNT_LOCKED, HttpStatus.FORBIDDEN);
    }

    public static AlertException BadCredentials() {
        return new UserException("401", INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }

    public static AlertException AuthenticationFailed() {
        return new UserException("401", AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED);
    }
}
