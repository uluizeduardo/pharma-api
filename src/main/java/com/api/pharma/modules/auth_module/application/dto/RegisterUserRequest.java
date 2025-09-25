package com.api.pharma.modules.auth_module.application.dto;

public record RegisterUserRequest(String username, String email, String password, String role) {
}
