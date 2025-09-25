package com.api.pharma.modules.auth_module.application.ports.in;


import com.api.pharma.modules.auth_module.application.dto.AuthenticationResponse;

public interface RefreshTokenInputPort {
    AuthenticationResponse refreshToken(String  refreshToken);
}
