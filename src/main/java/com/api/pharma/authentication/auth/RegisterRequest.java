package com.api.pharma.authentication.auth;

import com.api.pharma.model.enums.Role;


public record RegisterRequest(
        String userName,
        String email,
        String password,
        boolean isActive,
        Role role) {
}
