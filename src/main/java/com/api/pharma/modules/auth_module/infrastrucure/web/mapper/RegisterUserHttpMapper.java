package com.api.pharma.modules.auth_module.infrastrucure.web.mapper;


import com.api.pharma.modules.auth_module.application.dto.RegisterUserRequest;
import com.api.pharma.modules.auth_module.infrastrucure.web.dto.RegisterUserHttpRequest;

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
}
