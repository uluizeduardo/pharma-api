package com.api.pharma.web.mapper;

import com.api.pharma.dto.AuthenticationResponse;
import com.api.pharma.web.dto.AuthenticationHttpResponse;

public class AuthenticationHttpMapper {

    private AuthenticationHttpMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("This is a utility class and cannot be instantiated");
    }

    /**
     * Converts an `AuthenticationHttpResponse` object from the HTTP layer
     * into an `AuthenticationResponse` object for the application layer.
     *
     * @param httpResponse the HTTP response containing the access token and refresh token
     * @return an `AuthenticationResponse` object containing the access token
     *         and refresh token for the application layer
     */
    public static AuthenticationHttpResponse toApplication(AuthenticationHttpResponse httpResponse) {
        return new AuthenticationHttpResponse(
                httpResponse.accessToken(),
                httpResponse.refreshToken()
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
