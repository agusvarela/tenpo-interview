package com.tenpo.interview.service;

import com.tenpo.interview.entities.UserEntity;
import com.tenpo.interview.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test will test the user service.
 *
 * @author Agustin-Varela
 */
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private static final String TEST_USER = "testuser";
    private static final String PASSWORD = "password";
    private static final String USER_NOT_FOUND_WITH_USERNAME = "User not found with username: ";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenLoadUserByUsernameShouldBeOk() {
        UserEntity userEntity = UserEntity.builder()
                .username(TEST_USER)
                .password(PASSWORD)
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        UserDetails userDetails = userService.loadUserByUsername(TEST_USER);

        assertEquals(TEST_USER, userDetails.getUsername());

        verify(userRepository, times(INTEGER_ONE)).findByUsername(anyString());
    }

    @Test
    public void whenLoadUserByUsernameThenShouldThrowAnException() {
        try {
            assertThrows(UsernameNotFoundException.class,() ->  userService.loadUserByUsername(TEST_USER));
        } catch (Exception exception) {
            assertTrue(exception instanceof UsernameNotFoundException);
            assertEquals(USER_NOT_FOUND_WITH_USERNAME + TEST_USER, exception.getMessage());
        }
    }
}
