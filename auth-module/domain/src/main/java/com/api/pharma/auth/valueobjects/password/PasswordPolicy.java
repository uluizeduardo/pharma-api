package com.api.pharma.auth.valueobjects.password;

/**
 * Defines the contract for validating plain text passwords
 * against domain-specific rules such as minimum length,
 * character composition, and complexity.
 *
 * Implementations of this interface enforce password strength policies.
 */
public interface PasswordPolicy {
    void validate(String password);
}
