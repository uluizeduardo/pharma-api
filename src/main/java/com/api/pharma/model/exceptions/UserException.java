package com.api.pharma.model.exceptions;

import com.api.pharma.config.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class UserException extends AlertException {

    private static final String USER_NOT_FOUND  = "User not found!";
    public UserException(String code, String message, HttpStatus httpStatus) {
        super(code, message, httpStatus);
    }

    public static AlertException UserNotFound() {
        return new UserException("404", USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    public static AlertException UserEmailNotFound(String email) {
        return new UserException("404", "User not found to email: " + email, HttpStatus.NOT_FOUND);
    }
}
