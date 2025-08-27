package com.api.pharma.modules.auth_module.application.exceptions;


import com.api.pharma.modules.auth_module.application.exceptions.constants.UserMessages;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;
import java.util.function.Supplier;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    private static final Map<Class <? extends AuthenticationException>, Supplier<UserException>> AUTHENTICATION_EXCEPTIONS =
            Map.of(
                    DisabledException.class, UserException::accountDisabled,
                    LockedException.class, UserException::accountBlocked,
                    BadCredentialsException.class, UserException::badCredentials
            );

    public static UserException userNotFound() {
        return new UserException(UserMessages.USER_NOT_FOUND);
    }
    public static UserException userEmailNotFound(String email) {
        return new UserException(UserMessages.USER_NOT_FOUND_BY_EMAIL + email);
    }

    public static UserException emailAlreadyInUse(String email) {
        return new UserException(UserMessages.EMAIL_ALREADY_IN_USE);
    }

    public static UserException accountDisabled() {
        return new UserException(UserMessages.ACCOUNT_DISABLED);
    }

    public static UserException accountBlocked() {
        return new UserException(UserMessages.ACCOUNT_LOCKED);
    }

    public static UserException badCredentials() {
        return new UserException(UserMessages.INVALID_CREDENTIALS);
    }

    public static UserException authenticationFailed() {
        return new UserException(UserMessages.AUTHENTICATION_FAILED);
    }

    public static UserException fromAuthenticationFailed(AuthenticationException e) {
        return AUTHENTICATION_EXCEPTIONS.getOrDefault(e.getClass(), UserException::authenticationFailed).get();
    }

    public static UserException errorCreatingUser() {
        return new UserException(UserMessages.ERROR_CREATING_USER);
    }
}
