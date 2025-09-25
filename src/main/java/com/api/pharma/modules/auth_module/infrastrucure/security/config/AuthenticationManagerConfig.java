package com.api.pharma.modules.auth_module.infrastrucure.security.config;

import com.api.pharma.modules.auth_module.infrastrucure.persistence.exceptions.UserException;
import com.api.pharma.modules.auth_module.application.ports.out.AuthenticationManagerOutputPort;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerConfig implements AuthenticationManagerOutputPort {

    private final AuthenticationManager springAuthenticationManager;

    public AuthenticationManagerConfig(AuthenticationManager springAuthenticationManager) {
        this.springAuthenticationManager = springAuthenticationManager;
    }

    @Override
    public void authenticate(String username, String password) {
        try {
            springAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            throw UserException.fromAuthenticationFailed(e);
        }
    }
}
