package com.api.pharma.authentication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class responsible for managing JSON Web Tokens (JWT) in the application.
 *
 * <p>This class provides utility methods for:
 * <ul>
 *   <li>Generating JWT tokens.</li>
 *   <li>Validating JWT tokens.</li>
 *   <li>Extracting claims or specific information from JWT tokens.</li>
 * </ul>
 *
 * <p>The JWTs are used for securing endpoints and ensuring only authenticated and
 * authorized users can access certain resources.
 *
 * <p>This class is annotated with {@link Service}, making it a Spring-managed bean.
 */
@Service
public class JwtService {

    @Value("${application.setting.jwt.secret}")
    private String secretKey;

    @Value("${application.setting.jwt.expiration}")
    private long tokenExpiration;

    @Value("${application.setting.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Value("${application.setting.jwt.algorithm}")
    private String algorithm;

    @Value("${application.setting.jwt.issuer}")
    private String issuer;

    /**
     * Extracts the username (subject) from the provided JWT token.
     *
     * @param token the JWT token
     * @return the username (subject) extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimsTFunction a function that specifies which claim to extract
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used to verify the JWT signature.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a new authentication token with additional claims.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user's details
     * @return the generated authentication token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, tokenExpiration);
    }

    /**
     * Generates a new refresh token for the given user.
     *
     * @param userDetails the user's details
     * @return the generated refresh token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(Map.of(), userDetails, refreshTokenExpiration);
    }

    /**
     * Builds a token with the specified claims, user details, and expiration time.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user's details
     * @param tokenExpiration the expiration time in milliseconds
     * @return the generated token
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long tokenExpiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.valueOf(algorithm))
                .compact();
    }

    /**
     * Validates the token by comparing its username with the user's details and checking expiration.
     *
     * @param token the JWT token
     * @param userDetails the user's details
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token has expired by comparing its expiration date with the current time.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date of the token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
