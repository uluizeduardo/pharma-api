package com.api.pharma.modules.auth_module.infrastrucure.web.mapper;


import com.api.pharma.modules.auth_module.application.dto.AuthenticationRequest;
import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;
import com.api.pharma.modules.auth_module.infrastrucure.web.dto.AuthenticationHttpRequest;
import com.api.pharma.modules.auth_module.infrastrucure.web.dto.AuthenticationHttpResponse;

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

    /**
     * Converts an `AuthenticationResponse` object from the application layer
     * into an `AuthenticationHttpResponse` object for the HTTP layer.
     *
     * @param authenticationResponse the application layer's authentication response containing
     *                               the access token and refresh token
     * @return an `AuthenticationHttpResponse` object containing the access token
     *         and refresh token for the HTTP response
     */
    public static AuthenticationHttpResponse toHttpResponse (AuthenticationResponse authenticationResponse) {
        return new AuthenticationHttpResponse(
                authenticationResponse.accessToken(),
                authenticationResponse.refreshToken()
        );
    }
}
