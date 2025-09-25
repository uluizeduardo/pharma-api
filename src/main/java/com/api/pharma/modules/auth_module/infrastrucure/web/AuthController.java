package com.api.pharma.modules.auth_module.infrastrucure.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.api.pharma.modules.auth_module.application.ports.in.AuthenticateUserInputPort;
import com.api.pharma.modules.auth_module.application.ports.in.RefreshTokenInputPort;
import com.api.pharma.modules.auth_module.application.ports.in.RegisterUserInputPort;
import com.api.pharma.modules.auth_module.infrastrucure.web.dto.AuthenticationHttpRequest;
import com.api.pharma.modules.auth_module.infrastrucure.web.dto.RegisterUserHttpRequest;
import com.api.pharma.modules.auth_module.infrastrucure.web.mapper.AuthenticationHttpMapper;
import com.api.pharma.modules.auth_module.infrastrucure.web.mapper.RegisterUserHttpMapper;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/v1/api-pharma/auth", produces = "application/json")
public class AuthController {

    private final RegisterUserInputPort registerUserInputPort;
    private final RefreshTokenInputPort refreshTokenInputPort;
    private final AuthenticateUserInputPort authenticateUserInputPort;

    @Value("${app.cookie.secure}")
    private boolean isCookieSecure;

    public AuthController(RegisterUserInputPort registerUserInputPort,
                          RefreshTokenInputPort refreshTokenInputPort,
                          AuthenticateUserInputPort authenticateUserInputPort) {
        this.registerUserInputPort = registerUserInputPort;
        this.refreshTokenInputPort = refreshTokenInputPort;
        this.authenticateUserInputPort = authenticateUserInputPort;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register (@RequestBody @Valid RegisterUserHttpRequest request,
                                          HttpServletResponse response) {

        var appRequest   = RegisterUserHttpMapper.toApplication(request);
        var appResponse  = registerUserInputPort.registerUser(appRequest);

        addAuthCookies(response, appResponse.accessToken(), appResponse.refreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login (@RequestBody @Valid AuthenticationHttpRequest request,
                                       HttpServletResponse response) {

        var appRequest = AuthenticationHttpMapper.toApplication(request);
        var appResponse = authenticateUserInputPort.authenticate(appRequest);

        addAuthCookies(response, appResponse.accessToken(), appResponse.refreshToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken (HttpServletRequest request,
                                              HttpServletResponse response){

        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token not found in cookies");
        }

        var appResponse = refreshTokenInputPort.refreshToken(refreshToken);

        addAuthCookies(response, appResponse.accessToken(), appResponse.refreshToken());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkSession")
    public ResponseEntity<Void> checkSession() {
        return ResponseEntity.ok().build();
    }

    private void addAuthCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(isCookieSecure);
        accessTokenCookie.setMaxAge(60 * 60); // 1 hour
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(isCookieSecure);
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
    }
}
