package com.tenpo.interview.rest;

import com.tenpo.interview.entities.UserEntity;
import com.tenpo.interview.exception.ApiError;
import com.tenpo.interview.model.request.LoginRequest;
import com.tenpo.interview.model.request.UserRegistryRequest;
import com.tenpo.interview.model.response.LoginResponse;
import com.tenpo.interview.model.response.LogoutResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.tenpo.interview.util.TenpoConstants.BAD_REQUEST_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.CONFLICT_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.FORBIDDEN_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.METHOD_NOT_ALLOWED_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.OK_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.UNAUTHORIZED_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This controller is used to register, login or delete a user.
 *
 * @author Agustin-Varela
 */
@Api(value = "users")
@RequestMapping(value = "/users")
@CrossOrigin
public interface UserController {

    /**
     * This endpoint will create a new user
     *
     * @param userRegistryRequest Represents the user info.
     * @return the user entity {@link UserEntity}
     */
    @ApiOperation(value = "User Registration.", notes = "This endpoint will sign up a new user.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = SC_OK, message = OK_MESSAGE, response = UserEntity.class),
                    @ApiResponse(code = SC_METHOD_NOT_ALLOWED, message = METHOD_NOT_ALLOWED_MESSAGE,
                            response = ApiError.class),
                    @ApiResponse(code = SC_CONFLICT, message = CONFLICT_MESSAGE, response = ApiError.class),
                    @ApiResponse(code = SC_UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE, response = ApiError.class),
                    @ApiResponse(code = SC_BAD_REQUEST, message = BAD_REQUEST_MESSAGE, response = ApiError.class)
            })
    @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserEntity> userRegistry(@RequestBody @Valid UserRegistryRequest userRegistryRequest);

    /**
     * This endpoint will log in a user that is registered in the system
     *
     * @param loginRequest Represents the user info for the login.
     * @return the login response entity {@link LoginResponse}
     */
    @ApiOperation(value = "User Login.", notes = "This endpoint will login a user that was successfully registered.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = SC_OK, message = OK_MESSAGE, response = LoginResponse.class),
                    @ApiResponse(code = SC_FORBIDDEN, message = FORBIDDEN_MESSAGE, response = ApiError.class),
                    @ApiResponse(code = SC_BAD_REQUEST, message = BAD_REQUEST_MESSAGE, response = ApiError.class),
                    @ApiResponse(code = SC_METHOD_NOT_ALLOWED, message = METHOD_NOT_ALLOWED_MESSAGE,
                            response = ApiError.class)
            })
    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest);

    /**
     * This endpoint will log out a user that is registered in the system
     *
     * @return the successful logout
     */
    @ApiOperation(value = "User Logout.", notes = "This endpoint will logout a user that was successfully registered.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "BEARER {token}")
    @ApiResponses(
            value = {
                    @ApiResponse(code = SC_OK, message = OK_MESSAGE, response = LogoutResponse.class),
                    @ApiResponse(code = SC_UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE, response = ApiError.class)
            })
    @DeleteMapping(value = "/logout", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<LogoutResponse> logoutUser(@RequestHeader(value="Authorization") @NotEmpty String token);
}
