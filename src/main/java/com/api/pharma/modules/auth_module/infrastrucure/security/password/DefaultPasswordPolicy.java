package com.api.pharma.modules.auth_module.infrastrucure.security.password;

import com.api.pharma.modules.auth_module.domain.valueobjects.password.PasswordPolicy;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.WeakPasswordException;
import org.springframework.stereotype.Component;

/**
 * Default implementation of PasswordPolicy with configurable rules:
 * - Minimum length
 * - Requirement for uppercase letters
 * - Requirement for digits
 * - Requirement for special characters
 *
 * Can be extended or replaced by a more dynamic policy as needed.
 */
@Component
public class DefaultPasswordPolicy implements PasswordPolicy {

    private final int minLength;
    private final boolean requiredUppercase;
    private final boolean requireDigit;
    private final boolean requireSpecialChar;

    public DefaultPasswordPolicy() {
        this(8, true, true, true);
    }

    public DefaultPasswordPolicy(int minLength, boolean requiredUppercase, boolean requiredDigit, boolean requiredSpecialChar) {
        this.minLength = minLength;
        this.requiredUppercase = requiredUppercase;
        this.requireDigit = requiredDigit;
        this.requireSpecialChar = requiredSpecialChar;
    }

    @Override
    public void validate(String password) {
        if(password.length() < minLength) {
            throw new WeakPasswordException("Password must have at least " + minLength + " characters.");        }

        if(requiredUppercase && password.chars().noneMatch(Character::isUpperCase)) {
            throw new WeakPasswordException("Password must contain at least one uppercase letter.");
        }

        if (requireDigit && password.chars().noneMatch(Character::isDigit)) {
            throw new WeakPasswordException("Password must contain at least one digit.");
        }

        if (requireSpecialChar && password.chars().noneMatch(c -> "!@#$%^&*()_+-=|\\{}[]:;\"'<>,.?/~`".indexOf(c) >= 0)) {
            throw new WeakPasswordException("Password must contain at least one special character.");
        }
    }
}
