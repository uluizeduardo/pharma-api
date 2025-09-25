package com.api.pharma.modules.auth_module.application.ports.out;

public interface AuthenticationManagerOutputPort {
    void authenticate(String username, String password);
}

