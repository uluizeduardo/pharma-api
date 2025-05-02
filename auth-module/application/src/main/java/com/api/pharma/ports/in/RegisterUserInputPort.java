package com.api.pharma.ports.in;

import com.api.pharma.dto.RegisterUserRequest;
import com.api.pharma.dto.RegisterUserResponse;

public interface RegisterUserInputPort {
    RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest);
}
