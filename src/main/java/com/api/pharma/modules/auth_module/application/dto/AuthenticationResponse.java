package com.api.pharma.modules.auth_module.application.dto;

public record AuthenticationResponse (String accessToken, String refreshToken) {
}
