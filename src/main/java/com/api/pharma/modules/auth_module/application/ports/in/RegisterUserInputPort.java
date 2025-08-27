package com.api.pharma.modules.auth_module.application.ports.in;


import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;
import com.api.pharma.modules.auth_module.application.dto.RegisterUserRequest;

public interface RegisterUserInputPort {
    AuthenticationResponse registerUser(RegisterUserRequest registerUserRequest);
}
