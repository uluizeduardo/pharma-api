package com.api.pharma.authentication.config;

import com.api.pharma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration class for setting up application-level components and services.
 *
 * <p>This class is annotated with {@code @Configuration}, which indicates
 * that it declares beans and configuration for the Spring container.
 * It provides essential configurations such as:
 * <ul>
 *   <li>User authentication and authorization.</li>
 *   <li>Custom password encoding.</li>
 *   <li>CORS filter configuration.</li>
 * </ul>
 *
 * <p>The class also uses {@code @Value} to inject properties from the
 * application's configuration file (e.g., application.properties or application.yml).
 */
@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Value("${application.security.cors.allowed-origins}")
    private String origins;

    @Value("${application.security.cors.allowed-methods}")
    private String methods;

    public ApplicationConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a {@link UserDetailsService} bean that retrieves user details
     * from the database using the {@code UserRepository}.
     *
     * @return an instance of {@link UserDetailsService}.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Configures an {@link AuthenticationProvider} bean for user authentication.
     * <p>
     * This provider uses the {@link UserDetailsService} and a password encoder for validating users.
     *
     * @return an instance of {@link AuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Configures a {@link PasswordEncoder} bean using the {@link BCryptPasswordEncoder}.
     * <p>
     * This encoder hashes passwords using the BCrypt hashing algorithm.
     *
     * @return an instance of {@link PasswordEncoder}.
     */
    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures an {@link AuthenticationManager} bean to manage authentication.
     *
     * @param config the {@link AuthenticationConfiguration} provided by Spring Security.
     * @return an instance of {@link AuthenticationManager}.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures a {@link CorsFilter} bean to enable Cross-Origin Resource Sharing (CORS).
     * <p>
     * The allowed origins and methods are defined in the application properties.
     *
     * @return an instance of {@link CorsFilter}.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =  new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin(origins);
        config.addAllowedMethod(methods);
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
