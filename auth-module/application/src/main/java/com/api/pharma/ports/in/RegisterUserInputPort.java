package com.api.pharma.ports.in;

import com.api.pharma.dto.AuthenticationResponse;
import com.api.pharma.dto.RegisterUserRequest;

public interface RegisterUserInputPort {
    AuthenticationResponse registerUser(RegisterUserRequest registerUserRequest);
}
