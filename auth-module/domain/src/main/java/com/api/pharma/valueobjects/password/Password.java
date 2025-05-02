package com.api.pharma.valueobjects.password;

/**
 * Represents a text password that must be validated according to a PasswordPolicy
 * before being accepted into the system.
 *
 * This Value Object is immutable and guarantees that any instance is valid.
 */
public class Password {

    private final String value;

    public Password(String password, PasswordPolicy policy){
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        policy.validate(password);
        this.value = password;
    }

    public String getValue() {
        return value;
    }

    public HashedPassword hashWith(PasswordHasher hasher){
        return hasher.hash(this);
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}
