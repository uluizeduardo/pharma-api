package com.api.pharma.modules.auth_module.domain.valueobjects.password;

/**
 * Domain exception thrown when a password does not meet
 * the criteria defined by the active PasswordPolicy.
 *
 * This exception should be used to enforce password strength rules
 * at the domain level before persisting or using a password.
 */
public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException(String message) {
        super(message);
    }
}
