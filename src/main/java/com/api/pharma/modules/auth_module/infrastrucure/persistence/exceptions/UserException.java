package com.api.pharma.modules.auth_module.infrastrucure.persistence.exceptions;

import com.api.pharma.modules.auth_module.infrastrucure.security.config.exception_handler.AlertException;
import com.api.pharma.modules.auth_module.infrastrucure.utilities.constants.UserMessages;
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
        super(message, httpStatus);
    }

    public static AlertException userNotFound() {
        return new UserException(UserMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    public static AlertException userEmailNotFound(String email) {
        return new UserException(UserMessages.USER_NOT_FOUND_BY_EMAIL + email, HttpStatus.NOT_FOUND);
    }

    public static AlertException emailAlreadyInUse(String email) {
        return new UserException(UserMessages.EMAIL_ALREADY_IN_USE, HttpStatus.CONFLICT);
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

    public static AlertException errorCreatingUser() {
        return new UserException(UserMessages.ERROR_CREATING_USER, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
