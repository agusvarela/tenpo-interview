package com.tenpo.interview.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test will test JWT Service.
 *
 * @author Agustin-Varela
 */
class JWTServiceTest {

    @InjectMocks
    private JWTService jwtService;

    @Mock
    private Authentication authentication;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private UserDetails userDetails;

    private static final String TEST_USER = "testuser";
    private static final String SECRET = "testsecret";
    private static final int TTL = 60000;
    private static final int TTL2 = 100;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "ttl", TTL);
    }

    @Test
    public void getUsernameFromTokenShouldBeSuccessful() {
        String jwtToken = buildJwt(new Date(System.currentTimeMillis() + TTL));
        String actualUser = jwtService.getUsernameFromToken(jwtToken);

        assertEquals(TEST_USER, actualUser);
    }

    @Test
    public void getExpirationDateFromTokenShouldNotExpired() {
        String jwtToken = buildJwt(new Date(System.currentTimeMillis() + TTL));
        boolean isValid = jwtService.isTokenExpired(jwtToken);

        assertFalse(isValid);
    }

    @Test
    public void whenGenerateJWTTokenThenShouldNotBeNull() {
        Collection grantedAuthorityList = new ArrayList<>();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(TEST_USER);
        when(authentication.getAuthorities()).thenReturn(grantedAuthorityList);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        String actualToken = jwtService.generateJWTToken(authentication);

        assertNotNull(actualToken);

        verify(authentication, times(INTEGER_ONE)).getPrincipal();
        verify(userDetails, times(INTEGER_ONE)).getUsername();
        verify(authentication, times(INTEGER_ONE)).getAuthorities();
        verify(redisTemplate, times(INTEGER_ONE)).opsForValue();
    }

    @Test
    public void whenValidateJWTTokenThenShouldBeOK() {
        String jwtToken = buildJwt(new Date(System.currentTimeMillis() + TTL));

        when(redisTemplate.hasKey(anyString())).thenReturn(true);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(anyString())).thenReturn(jwtToken);

        boolean result = jwtService.validateToken(jwtToken);

        assertTrue(result);

        verify(redisTemplate, times(INTEGER_ONE)).hasKey(anyString());
        verify(redisTemplate, times(INTEGER_ONE)).opsForValue();
        verify(valueOperations, times(INTEGER_ONE)).get(anyString());
    }

    @Test
    public void whenValidateTokenThenShouldThrowAnException() {
        String testToken = buildJwt(new Date(System.currentTimeMillis() - TTL2));

        try {
            assertThrows(ExpiredJwtException.class, () ->  jwtService.validateToken(testToken));
        } catch (Exception exception) {
            assertTrue(exception instanceof ExpiredJwtException);
        }
    }

    private String buildJwt(Date expirationDate) {
        return Jwts.builder()
                .setSubject(TEST_USER)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}