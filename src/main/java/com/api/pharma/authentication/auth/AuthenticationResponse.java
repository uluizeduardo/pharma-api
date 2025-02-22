package com.api.pharma.authentication.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing authentication tokens and user context")
public record AuthenticationResponse(
        @Schema(
                description = "Short-lived JWT access token for API authorization",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                required = true
        )
        @JsonProperty("access_token")
        String accessToken,

        @Schema(
                description = "Long-lived refresh token for token renewal",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                required = true
        )
        @JsonProperty("refresh_token")
        String refreshToken) {
}
