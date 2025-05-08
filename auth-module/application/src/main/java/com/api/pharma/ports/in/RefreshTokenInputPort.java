package com.api.pharma.ports.in;

import com.api.pharma.dto.AuthenticationResponse;

public interface RefreshTokenInputPort {
    AuthenticationResponse refreshToken(String  refreshToken);
}
