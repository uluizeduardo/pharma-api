package com.api.pharma.valueobjects.password;

/**
 * Represents a hashed password that has been securely encrypted
 * using a hashing algorithm such as BCrypt.
 *
 * This Value Object allows secure comparison between plain and hashed passwords,
 * and prevents exposure of sensitive values.
 */
public class HashedPassword {

    private final String value;

    public HashedPassword(String hashed) {
        if (hashed == null || hashed.isBlank()) {
            throw new IllegalArgumentException("Hashed password cannot be empty.");
        }
        this.value = hashed;
    }

    public boolean matches(Password plain, PasswordHasher hasher) {
        return hasher.matches(plain, this);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[HASHED]";
    }
}
