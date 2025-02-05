package com.api.pharma.authentication.auth;

import com.api.pharma.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Schema(description = "Payload for user registration")
public record RegisterRequest(
        @Schema(description = "Username of the new user", example = "John Doe", required = true)
        @NotBlank(message = "O Campo Nome não pode ser vazio")
        String userName,

        @Schema(description = "User email", example = "john.doe@example.com", required = true)
        @NotBlank(message = "O Campo Email não pode ser vazio")
        String email,
        @Schema(description = "User password", example = "securePassword123", required = true)
        @NotBlank(message = "O Campo Senha não pode ser vazio")
        String password,

        @Schema(description = "Activation status of the user", example = "true")
        boolean isActive,
        @Schema(description = "User role", example = "PHARMACIST", required = true)
        @NotNull(message = "O Campo Role não pode nulo")
        Role role) {
}
