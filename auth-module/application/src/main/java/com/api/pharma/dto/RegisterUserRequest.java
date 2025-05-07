package com.api.pharma.dto;

public record RegisterUserRequest(String username, String email, String password, String role) {
}
