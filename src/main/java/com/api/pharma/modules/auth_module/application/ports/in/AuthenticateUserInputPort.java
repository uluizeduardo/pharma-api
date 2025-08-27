package com.api.pharma.modules.auth_module.application.ports.in;


import com.api.pharma.modules.auth_module.application.dto.AuthenticationRequest;
import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;

public interface AuthenticateUserInputPort {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
