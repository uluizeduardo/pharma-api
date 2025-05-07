package com.api.pharma.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request payload for user registration")
public record RegisterUserHttpRequest(

        @NotBlank
        @Schema(description = "The username of the new user", example = "johndoe")
        String username,

        @NotBlank
        @Email
        @Schema(description = "The email address of the user", example = "johndoe@example.com")
        String email,

        @NotBlank
        @Schema(description = "The password for the user account", example = "StrongP@ssw0rd!")
        String password,

        @NotBlank
        @Schema(description = "The role of the user", example = "CUSTOMER")
        String role

) {

}