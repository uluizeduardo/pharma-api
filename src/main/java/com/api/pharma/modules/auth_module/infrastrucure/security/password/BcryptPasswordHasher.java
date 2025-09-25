package com.api.pharma.modules.auth_module.infrastrucure.security.password;

import com.api.pharma.modules.auth_module.domain.valueobjects.password.HashedPassword;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.Password;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Implementation of PasswordHasher using the BCrypt algorithm.
 *
 * BCrypt automatically manages salting and provides strong protection
 * against brute force and rainbow table attacks.
 */
@Component
public class BcryptPasswordHasher implements PasswordHasher {

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
