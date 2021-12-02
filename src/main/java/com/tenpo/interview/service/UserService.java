package com.tenpo.interview.service;

import com.tenpo.interview.entities.UserEntity;
import com.tenpo.interview.exception.ApiError;
import com.tenpo.interview.exception.ApiException;
import com.tenpo.interview.model.request.UserRegistryRequest;
import com.tenpo.interview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * This service will process all related to the uses like as registration or login.
 *
 * @author Agustin-Varela
 */
@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private static final String USER_HAS_BEEN_FOUND = "User: {}, has been found";
    private static final String FINDING_USER = "Finding user: {}";
    private static final String USER_NOT_FOUND_WITH_USERNAME = "User not found with username: ";
    private static final String USER_ROLE = "USER_ROLE";
    private static final String USER_OR_EMAIL_ALREADY_EXISTS_ERROR_DESCRIPTION =
            "User or email already registered.";
    private static final String USERNAME_OR_EMAIL_ALREADY_EXISTS_LOG_MESSAGE =
            "Username or email already exists. Username: {}";


    /**
     * Creates a new user based on request info.
     *
     * @param userRegistryRequest Represents the user info.
     * @return the user entity {@link UserEntity}
     */
    public UserEntity registerUser(UserRegistryRequest userRegistryRequest) {
        checkUserOrEmailAlreadyExists(userRegistryRequest.getUsername().trim(), userRegistryRequest.getEmail());

        return userRepository.save(
                UserEntity.builder()
                        .username(userRegistryRequest.getUsername())
                        .email(userRegistryRequest.getEmail())
                        .password(passwordEncoder.encode(userRegistryRequest.getPassword()))
                        .build()
        );
    }

    private void checkUserOrEmailAlreadyExists(final String username, final String email) {
        if (Objects.nonNull(userRepository.findByUsername(username)) ||
                Objects.nonNull(userRepository.findByEmail(email))) {

            log.error(USERNAME_OR_EMAIL_ALREADY_EXISTS_LOG_MESSAGE, username);

            ApiError error = ApiError.builder()
                    .errorType(CONFLICT.getReasonPhrase())
                    .errorDescription(USER_OR_EMAIL_ALREADY_EXISTS_ERROR_DESCRIPTION)
                    .build();

            throw new ApiException(error, CONFLICT);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info(FINDING_USER, username);

        UserEntity user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(USER_NOT_FOUND_WITH_USERNAME + username);
        }
        log.info(USER_HAS_BEEN_FOUND, username);

        return new User(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(USER_ROLE));
    }
}
