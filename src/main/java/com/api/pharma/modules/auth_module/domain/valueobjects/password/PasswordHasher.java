package com.api.pharma.modules.auth_module.domain.valueobjects.password;

/**
 * Defines a strategy interface for hashing and matching passwords.
 * Implementations can use different algorithms (e.g., BCrypt, Argon2, PBKDF2).
 *
 * This abstraction allows switching the hashing algorithm without
 * changing the core domain logic.
 */
public interface PasswordHasher {
    HashedPassword hash(Password password);
    boolean matches(Password password, HashedPassword hashedPassword);
}
