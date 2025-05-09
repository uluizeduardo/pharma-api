package com.api.pharma.dto;

public record AuthenticationResponse (String accessToken, String refreshToken) {
}
