package com.api.pharma.authentication.auth;

import com.api.pharma.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RegisterRequest(
        @NotBlank(message = "O Campo Nome não pode ser vazio") String userName,
        @NotBlank(message = "O Campo Email não pode ser vazio") String email,
        @NotBlank(message = "O Campo Senha não pode ser vazio") String password,
        boolean isActive,
        @NotNull(message = "O Campo Role não pode nulo") Role role) {
}
