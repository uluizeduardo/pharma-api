package com.api.pharma.ports.out;

public interface AuthenticationManagerOutputPort {
    void authenticate(String username, String password);
}

