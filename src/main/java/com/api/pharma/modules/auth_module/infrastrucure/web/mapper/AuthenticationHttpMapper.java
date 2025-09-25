package com.api.pharma.modules.auth_module.infrastrucure.web.mapper;


import com.api.pharma.modules.auth_module.application.dto.AuthenticationRequest;
import com.api.pharma.modules.auth_module.infrastrucure.web.dto.AuthenticationHttpRequest;

public class AuthenticationHttpMapper {

    private AuthenticationHttpMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("This is a utility class and cannot be instantiated");
    }


    /**
     * Converts an `AuthenticationHttpRequest` object from the HTTP layer
     * into an `AuthenticationRequest` object for the application layer.
     *
     * @param httpRequest the HTTP request containing the email and password
     * @return an `AuthenticationRequest` object containing the email and password
     */
    public static AuthenticationRequest toApplication(AuthenticationHttpRequest httpRequest) {
        return new AuthenticationRequest(
                httpRequest.email(),
                httpRequest.password()
        );
    }
}
