package com.api.pharma.valueobjects.password;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Implementation of PasswordHasher using the BCrypt algorithm.
 *
 * BCrypt automatically manages salting and provides strong protection
 * against brute force and rainbow table attacks.
 */
public class BcryptPasswordHasher implements PasswordHasher{

    @Override
    public HashedPassword hash(Password password) {
        String hashed = BCrypt.hashpw(password.getValue(), BCrypt.gensalt());
        return new HashedPassword(hashed);
    }

    @Override
    public boolean matches(Password password, HashedPassword hashedPassword) {
        return BCrypt.checkpw(password.getValue(), hashedPassword.getValue());
    }
}
