package com.api.pharma.security.config;

import com.api.pharma.security.filter.JwtAuthenticationFilter;
import com.api.pharma.utilities.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


import static com.api.pharma.enums.Role.MANAGER;
import static com.api.pharma.enums.Role.PHARMACIST;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * Security configuration class for defining the application's security settings.
 *
 * <p>This class configures Spring Security to manage authentication, authorization,
 * session management, and logout processes. It also integrates a JWT-based
 * authentication filter to handle stateless authentication.
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Configuration} - Marks this class as a Spring configuration class.</li>
 *   <li>{@code @EnableWebSecurity} - Enables Spring Security's web security support.</li>
 *   <li>{@code @EnableMethodSecurity} - Allows method-level security annotations, such as {@code @PreAuthorize}.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    private final LogoutHandler logoutHandler;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                                 AuthenticationProvider authenticationProvider,
                                 LogoutHandler logoutHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
        this.logoutHandler = logoutHandler;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * <p>This method defines:
     * <ul>
     *   <li>CSRF protection is disabled for stateless authentication.</li>
     *   <li>Authorization rules for API endpoints:</li>
     *     <ul>
     *       <li>Endpoints under {@code v1/api-pharma/auth/**} are publicly accessible.</li>
     *       <li>Endpoints under {@code v1/api-pharma/user} require roles {@code MANAGER} or {@code PHARMACIST}.</li>
     *     </ul>
     *   <li>Session management policy is set to {@code STATELESS} to avoid maintaining server-side sessions.</li>
     *   <li>A JWT-based authentication filter is added before the {@code UsernamePasswordAuthenticationFilter}.</li>
     *   <li>Custom logout handling, including a logout handler and clearing the security context upon successful logout.</li>
     * </ul>
     *
     * @param http the {@code HttpSecurity} object to configure the security settings
     * @return the configured {@code SecurityFilterChain} instance
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                                    // Public routes via constant
                                    .requestMatchers(StringUtils.WHITE_LIST_URL).permitAll()
                                    .requestMatchers(GET, "/v1/api-pharma/user").hasAnyRole(MANAGER.name(), PHARMACIST.name())
                                    .requestMatchers(POST, "/v1/api-pharma/user").hasAnyRole(MANAGER.name(), PHARMACIST.name())
                                    .anyRequest()
                                    .authenticated()
                )
                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/v1/api-pharma/auth/logout")
                              .addLogoutHandler(logoutHandler)
                              .logoutSuccessHandler((request, response, authenticationProvider) -> SecurityContextHolder.clearContext()))
                .build();

    }
}
