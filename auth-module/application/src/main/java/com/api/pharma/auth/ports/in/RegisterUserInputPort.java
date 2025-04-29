package com.api.pharma.auth.ports.in;

import com.api.pharma.auth.dto.RegisterUserRequest;
import com.api.pharma.auth.dto.RegisterUserResponse;

public interface RegisterUserInputPort {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
}
