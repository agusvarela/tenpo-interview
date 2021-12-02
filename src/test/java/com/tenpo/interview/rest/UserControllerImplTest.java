package com.tenpo.interview.rest;

import com.tenpo.interview.entities.UserEntity;
import com.tenpo.interview.model.request.LoginRequest;
import com.tenpo.interview.model.request.UserRegistryRequest;
import com.tenpo.interview.model.response.LoginResponse;
import com.tenpo.interview.model.response.LogoutResponse;
import com.tenpo.interview.rest.impl.UserControllerImpl;
import com.tenpo.interview.service.JWTService;
import com.tenpo.interview.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Date;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test will test the user controller impl.
 *
 * @author Agustin-Varela
 */
class UserControllerImplTest {

    @InjectMocks
    private UserControllerImpl userController;

    @Mock
    private UserService userService;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private static final String PASSWORD = "Test12345";
    private static final String EMAIL = "ejemplo@gmail.com";
    private static final String USERNAME = "testUser";
    private static final String TEST_USER = "testuser";
    private static final String SECRET = "testsecret";
    private static final String LOGOUT_WAS_SUCCESSFULLY = "Logout was successfully.";
    private static final int TTL = 60000;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenUserRegistryShouldBeOk() {
        UserRegistryRequest request = buildUserRegistryRequest();

        when(userService.registerUser(any())).thenReturn(buildUserEntity());

        ResponseEntity<UserEntity> response = userController.userRegistry(request);

        assertNotNull(response.getBody());
        assertEquals(request.getUsername(), response.getBody().getUsername());
        assertEquals(request.getEmail(), response.getBody().getEmail());

        verify(userService, times(INTEGER_ONE)).registerUser(any());
    }

    @Test
    public void whenLoginUserShouldBeOk() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtService.generateJWTToken(any())).thenReturn(buildJwt(new Date(System.currentTimeMillis() + TTL)));

        ResponseEntity<LoginResponse> response = userController.loginUser(buildLoginRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());

        verify(authenticationManager, times(INTEGER_ONE)).authenticate(any());
        verify(jwtService, times(INTEGER_ONE)).generateJWTToken(any());
    }

    @Test
    public void whenLogoutUserThenShouldBeOK() {
        doNothing().when(jwtService).removeUsername(any());

        ResponseEntity<LogoutResponse> response = userController
                .logoutUser(buildJwt(new Date(System.currentTimeMillis() + TTL)));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(LOGOUT_WAS_SUCCESSFULLY, response.getBody().getMessage());

        verify(jwtService, times(INTEGER_ONE)).removeUsername(any());
    }

    private UserRegistryRequest buildUserRegistryRequest() {
        return UserRegistryRequest.builder()
                .confirmPassword(PASSWORD)
                .password(PASSWORD)
                .email(EMAIL)
                .username(USERNAME)
                .build();
    }

    private UserEntity buildUserEntity() {
        return UserEntity.builder()
                .password(PASSWORD)
                .username(USERNAME)
                .email(EMAIL)
                .id(INTEGER_ONE)
                .build();
    }

    private LoginRequest buildLoginRequest() {
        return LoginRequest.builder()
                .password(PASSWORD)
                .username(USERNAME)
                .build();
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