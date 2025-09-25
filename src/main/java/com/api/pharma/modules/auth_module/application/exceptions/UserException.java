package com.api.pharma.modules.auth_module.application.exceptions;


import com.api.pharma.modules.auth_module.application.exceptions.constants.UserMessages;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

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

    public static UserException errorCreatingUser() {
        return new UserException(UserMessages.ERROR_CREATING_USER);
    }
}
