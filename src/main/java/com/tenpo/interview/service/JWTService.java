package com.tenpo.interview.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

/**
 * This service will process all related to the user authentication.
 *
 * @author Agustin-Varela
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JWTService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.time}")
    private int ttl;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String USER_ROLE = "USER_ROLE";

    /**
     * This method wil generate a new JWT Token for authenticated user.
     *
     * @param authentication Represents the username and password.
     */
    public String generateJWTToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_ROLE,
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );

        long now = System.currentTimeMillis();

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + ttl))
                .signWith(HS512, secretKey)
                .compact();

        redisTemplate.opsForValue().set(username, token, Duration.ofMillis(ttl));

        return token;
    }

    /**
     * This method will validate the token.
     *
     * @param token Represents the JWT token.
     * @return a boolean
     */
    public boolean validateToken(String token) {
        String username = getUsernameFromToken(token);

        return Objects.nonNull(username) && !isTokenExpired(token) &&
                redisTemplate.hasKey(username) && token.equals(redisTemplate.opsForValue().get(username));
    }

    /**
     * This method will get the username from the token.
     *
     * @param token Represents the JWT token.
     * @return the username as String
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * This method checks if the token expired.
     *
     * @param token Represents the JWT token.
     * @return a boolean
     */
    public boolean isTokenExpired(String token) {
        return getClaimFromToken(token, Claims::getExpiration).before(new Date());
    }

    /**
     * This method will retrieve a list of roles from a specific token.
     *
     * @param token Represents the JWT token.
     * @return a roles list
     */
    public List<String> getRoles(String token) {
        return getClaimFromToken(token, claims -> (List) claims.get(USER_ROLE));
    }

    /**
     * This method will remove a username from redis.
     *
     * @param token Represents the JWT token.
     */
    public void removeUsername(String token) {
        redisTemplate.delete(getUsernameFromToken(token));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody());
    }
}
