package com.api.pharma.security.config;

import com.api.pharma.persistence.exceptions.UserException;
import com.api.pharma.ports.out.AuthenticationManagerOutputPort;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationManager implements AuthenticationManagerOutputPort {

    private final AuthenticationManager authenticationManager;

    public AuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(username, password);
        } catch (AuthenticationException e) {
            throw UserException.fromAuthenticationFailed(e);
        }
    }
}
