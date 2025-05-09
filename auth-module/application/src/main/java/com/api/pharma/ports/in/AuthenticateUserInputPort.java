package com.api.pharma.ports.in;

import com.api.pharma.dto.AuthenticationRequest;
import com.api.pharma.dto.AuthenticationResponse;

public interface AuthenticateUserInputPort {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
