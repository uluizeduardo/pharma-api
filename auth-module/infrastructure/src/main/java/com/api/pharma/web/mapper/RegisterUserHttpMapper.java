package com.api.pharma.web.mapper;

import com.api.pharma.dto.RegisterUserRequest;
import com.api.pharma.dto.RegisterUserResponse;
import com.api.pharma.web.dto.AuthenticationHttpResponse;
import com.api.pharma.web.dto.RegisterUserHttpRequest;

public class RegisterUserHttpMapper {

    private RegisterUserHttpMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("This is a utility class and cannot be instantiated");
    }

    /**
     * Converts the HTTP request DTO into the application layer's RegisterUserRequest.
     *
     * @param httpRequest the HTTP request DTO
     * @return the application layer's RegisterUserRequest
     */
    public static RegisterUserRequest toApplication(RegisterUserHttpRequest httpRequest) {
        return new RegisterUserRequest(
                httpRequest.username(),
                httpRequest.email(),
                httpRequest.password(),
                httpRequest.role()
        );
    }

    /**
     * Converts the application layer's AuthenticationResponse into a
     * DTO appropriate for HTTP responses with proper field annotations.
     *
     * @param response the application layer's AuthenticationResponse
     * @return a DTO suitable for web layer (HTTP response)
     */
    public static AuthenticationHttpResponse toHttp(RegisterUserResponse response) {
        return new AuthenticationHttpResponse(
                response.accessToken(),
                response.refreshToken()
        );
    }
}
