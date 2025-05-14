package com.api.pharma.web;

import com.api.pharma.ports.in.AuthenticateUserInputPort;
import com.api.pharma.ports.in.RefreshTokenInputPort;
import com.api.pharma.ports.in.RegisterUserInputPort;
import com.api.pharma.web.dto.AuthenticationHttpRequest;
import com.api.pharma.web.mapper.AuthenticationHttpMapper;
import com.api.pharma.web.mapper.RegisterUserHttpMapper;
import com.api.pharma.web.dto.AuthenticationHttpResponse;
import com.api.pharma.web.dto.RegisterUserHttpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "api/auth", produces = "{application/json}")
public class AuthController {

    private final RegisterUserInputPort registerUserInputPort;
    private final RefreshTokenInputPort refreshTokenInputPort;
    private final AuthenticateUserInputPort authenticateUserInputPort;

    public AuthController(RegisterUserInputPort registerUserInputPort,
                          RefreshTokenInputPort refreshTokenInputPort,
                          AuthenticateUserInputPort authenticateUserInputPort) {
        this.registerUserInputPort = registerUserInputPort;
        this.refreshTokenInputPort = refreshTokenInputPort;
        this.authenticateUserInputPort = authenticateUserInputPort;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationHttpResponse> register (@RequestBody @Valid RegisterUserHttpRequest request) {

        var appRequest   = RegisterUserHttpMapper.toApplication(request);
        var appResponse  = registerUserInputPort.registerUser(appRequest);
        var httpResponse = AuthenticationHttpMapper.toHttpResponse(appResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(httpResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationHttpResponse> login (@RequestBody @Valid AuthenticationHttpRequest request) {

        var appRequest = AuthenticationHttpMapper.toApplication(request);
        var appResponse = authenticateUserInputPort.authenticate(appRequest);
        var httpResponse = AuthenticationHttpMapper.toHttpResponse(appResponse);

        return ResponseEntity.ok(httpResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationHttpResponse> refreshToken (HttpServletRequest request){
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing or invalid Authorization header");
        }

        final String refreshToken = authHeader.substring(7);

        var appResponse = refreshTokenInputPort.refreshToken(refreshToken);
        var httpResponse = AuthenticationHttpMapper.toHttpResponse(appResponse);

        return ResponseEntity.ok(httpResponse);
    }
}
