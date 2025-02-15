package com.api.pharma.model.exceptions;

import com.api.pharma.config.exceptionHandler.AlertException;
import com.api.pharma.utilities.constants.MessageConstants.*;
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

    public UserException(String message, HttpStatus httpStatus) {
        super(HttpStatusCodes.getErrorCode(httpStatus), message, httpStatus);
    }

    public static AlertException userNotFound() {
        return new UserException(UserMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    public static AlertException userEmailNotFound(String email) {
        return new UserException(UserMessages.USER_NOT_FOUND_BY_EMAIL + email, HttpStatus.NOT_FOUND);
    }

    public static AlertException accountDisabled() {
        return new UserException(UserMessages.ACCOUNT_DISABLED, HttpStatus.FORBIDDEN);
    }

    public static AlertException accountBlocked() {
        return new UserException(UserMessages.ACCOUNT_LOCKED, HttpStatus.FORBIDDEN);
    }

    public static AlertException badCredentials() {
        return new UserException(UserMessages.INVALID_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }

    public static AlertException authenticationFailed() {
        return new UserException(UserMessages.AUTHENTICATION_FAILED, HttpStatus.UNAUTHORIZED);
    }

    public static AlertException fromAuthenticationFailed(AuthenticationException e) {
        return AUTHENTICATION_EXCEPTIONS.getOrDefault(e.getClass(), UserException::authenticationFailed).get();
    }
}
