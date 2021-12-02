package com.tenpo.interview.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * This exception is specific for this service.
 *
 * @author Agustin-Varela
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    private ApiError apiError;
    private HttpStatus status;

    public ApiException(ApiError apiError, HttpStatus httpStatus) {
        this.apiError = apiError;
        this.status = httpStatus;
    }
}