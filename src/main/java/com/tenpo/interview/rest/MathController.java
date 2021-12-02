package com.tenpo.interview.rest;

import com.tenpo.interview.exception.ApiError;
import com.tenpo.interview.model.request.SumRequest;
import com.tenpo.interview.model.response.SumResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static com.tenpo.interview.util.TenpoConstants.BAD_REQUEST_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.METHOD_NOT_ALLOWED_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.OK_MESSAGE;
import static com.tenpo.interview.util.TenpoConstants.UNAUTHORIZED_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This controller is used to math operations for logged users.
 *
 * @author Agustin-Varela
 */
@Api(tags = "math")
@RequestMapping(value = "/math", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@CrossOrigin
public interface MathController {

    /**
     * This endpoint will sum two numbers.
     *
     * @param sumRequest Represents the numbers that will be added.
     * @return the sum response entity {@link SumResponse}
     */
    @ApiOperation(value = "Sum numbers.", notes = "This endpoint will sum two numbers.")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true,
            paramType = "header", dataTypeClass = String.class, example = "BEARER {token}")
    @ApiResponses(
            value = {
                    @ApiResponse(code = SC_OK, message = OK_MESSAGE, response = SumResponse.class),
                    @ApiResponse(code = SC_BAD_REQUEST, message = BAD_REQUEST_MESSAGE, response = ApiError.class),
                    @ApiResponse(code = SC_UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE, response = ApiError.class),
                    @ApiResponse(code = SC_METHOD_NOT_ALLOWED, message = METHOD_NOT_ALLOWED_MESSAGE,
                            response = ApiError.class)
            })
    @PostMapping(value = "/sum")
    ResponseEntity<SumResponse> sumNumbers(@RequestBody @Valid SumRequest sumRequest);
}
