package com.tenpo.interview.rest.impl;

import com.tenpo.interview.entities.UserEntity;
import com.tenpo.interview.model.request.LoginRequest;
import com.tenpo.interview.model.request.UserRegistryRequest;
import com.tenpo.interview.model.response.LoginResponse;
import com.tenpo.interview.model.response.LogoutResponse;
import com.tenpo.interview.rest.UserController;
import com.tenpo.interview.service.JWTService;
import com.tenpo.interview.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

/**
 * Register, login or delete a user.
 *
 * @author Agustin-Varela
 */
@RestController
@Slf4j
public class UserControllerImpl implements UserController {

    private static final String LOG_IN_USERNAME_LOG_MESSAGE = "Log in, username: {}";
    private static final String USER_REGISTRY_USERNAME_LOG_MESSAGE = "User registry, username: {}";
    private static final String LOGOUT_WAS_SUCCESSFULLY = "Logout was successfully.";
    private static final int BEGIN_INDEX = 7;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<UserEntity> userRegistry(UserRegistryRequest userRegistryRequest) {
        log.debug(USER_REGISTRY_USERNAME_LOG_MESSAGE, userRegistryRequest.getUsername());

        return ResponseEntity.ok(userService.registerUser(userRegistryRequest));
    }

    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {
        log.debug(LOG_IN_USERNAME_LOG_MESSAGE, loginRequest.getUsername());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(LoginResponse.builder()
                .jwtToken(jwtService.generateJWTToken(authentication))
                .build()
        );
    }

    @Override
    public ResponseEntity<LogoutResponse> logoutUser(String token) {
        jwtService.removeUsername(token.substring(BEGIN_INDEX));

        return ResponseEntity.ok(
                LogoutResponse.builder()
                        .message(LOGOUT_WAS_SUCCESSFULLY)
                        .build()
        );
    }
}
