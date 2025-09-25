package com.api.pharma.modules.auth_module.infrastrucure.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import com.api.pharma.modules.auth_module.infrastrucure.persistence.repository.TokenRepository;
import com.api.pharma.modules.auth_module.infrastrucure.security.token.JwtProvider;
import com.api.pharma.modules.auth_module.infrastrucure.utilities.StringUtils;

import java.io.IOException;
import java.util.Arrays;

/**
 * JwtAuthenticationFilter is a custom filter for handling JWT-based authentication.
 * It extends {@link OncePerRequestFilter}, ensuring that it is executed only once per request.
 *
 * This filter:
 * - Extracts the JWT token from the HTTP request header.
 * - Validates the token and checks if it is not expired or revoked.
 * - Authenticates the user by setting the security context if the token is valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Value("${application.setting.jwt.header}")
    private String header;

    public JwtAuthenticationFilter(JwtProvider jwtProvider,
                                    UserDetailsService userDetailsService,
                                    TokenRepository tokenRepository) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Processes the HTTP request to authenticate the user based on a JWT token.
     *
     * @param request     The HTTP request object.
     * @param response    The HTTP response object.
     * @param filterChain The filter chain to pass the request/response to the next filter.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException      If an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        // Skip authentication for specific paths, like the auth endpoint
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        // Rotas públicas (Swagger + Auth)
        boolean isWhiteListed = Arrays.stream(StringUtils.WHITE_LIST_URL)
                .anyMatch(patter -> antPathMatcher.match(patter, request.getRequestURI()));

        if (isWhiteListed) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the Authorization header.
        final String authHeader = request.getHeader(header);
        final String jwt;
        final String userEmail;

        // If the Authorization header is missing or doesn't start with "Bearer ", skip processing.
        if(authHeader == null && authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT from the Authorization header.
        jwt = authHeader.substring(7);
        userEmail = jwtProvider.extractUsername(jwt);

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            if(jwtProvider.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}