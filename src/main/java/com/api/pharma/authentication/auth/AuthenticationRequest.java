package com.api.pharma.authentication.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank(message = "O Campo Email não pode ser vazio") String email,
        @NotBlank(message = "O Campo Senha não pode ser vazio") String password) {
}
