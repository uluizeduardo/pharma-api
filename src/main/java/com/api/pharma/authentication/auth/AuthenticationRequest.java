package com.api.pharma.authentication.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload for user login")
public record AuthenticationRequest(
        @Schema(description = "User email", example = "john.doe@example.com", required = true)
        @NotBlank(message = "O Campo Email não pode ser vazio")
        String email,
        @Schema(description = "User password", example = "securePassword123", required = true)
        @NotBlank(message = "O Campo Senha não pode ser vazio")
        String password) {
}
