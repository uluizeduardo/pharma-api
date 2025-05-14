package com.api.pharma.security.config;

import com.api.pharma.persistence.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user logout operations and token invalidation.
 *
 * <p>This class is annotated with {@code @Service}, making it a Spring-managed service.
 * It implements {@link LogoutHandler}, providing a custom logout mechanism that:
 * <ul>
 *   <li>Extracts the JWT from the {@code Authorization} header of the request.</li>
 *   <li>Marks the token as expired and revoked in the database.</li>
 *   <li>Clears the security context to complete the logout process.</li>
 * </ul>
 *
 * <p>The service interacts with the following components:
 * <ul>
 *   <li>{@link TokenRepository}: To manage and update token status in the database.</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    /**
     * Handles user logout by invalidating the JWT token and clearing the security context.
     *
     * @param request        the HTTP request containing the {@code Authorization} header
     * @param response       the HTTP response
     * @param authentication the authentication object (may be null if the user is already logged out)
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
