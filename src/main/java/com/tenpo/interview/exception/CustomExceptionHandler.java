package com.tenpo.interview.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Handler for all the exceptions related to this service.
 *
 * @author Agustin-Varela
 */
@ControllerAdvice
@SuppressWarnings({"unchecked","rawtypes"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INVALID_PARAMETER = "Invalid parameter";
    private static final String INVALID_CREDENTIALS = "Invalid credentials.";
    private static final String SOME_ERROR_OCCURRED_WHILE_EXECUTING_THE_SERVICE =
            "Some error occurred while executing the service";
    private static final String THIS_METHOD_IS_NOT_SUPPORTED_BY_THE_SERVICE =
            "This method is not supported by the service";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<ApiError> apiErrorList = exception.getBindingResult().getAllErrors()
                .stream()
                .map(error -> buildApiError(INVALID_PARAMETER, error.getDefaultMessage(),
                        status.getReasonPhrase()))
                .collect(Collectors.toList());

        return new ResponseEntity(apiErrorList, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {

        ApiError error = buildApiError(THIS_METHOD_IS_NOT_SUPPORTED_BY_THE_SERVICE,
                exception.getMessage(), status.getReasonPhrase());

        return new ResponseEntity(error, status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptions(Exception exception) {
        ApiError error = buildApiError(SOME_ERROR_OCCURRED_WHILE_EXECUTING_THE_SERVICE,
                exception.getMessage(), INTERNAL_SERVER_ERROR.getReasonPhrase());

        return new ResponseEntity(error, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<Object> handleExceptions(ApiException apiException) {
        return new ResponseEntity(apiException.getApiError(), apiException.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleUserBadCredentialsException(BadCredentialsException exception,
                                                                          WebRequest request) {

        ApiError error = buildApiError(INVALID_CREDENTIALS, exception.getMessage(),
                FORBIDDEN.getReasonPhrase());

        return new ResponseEntity(error, FORBIDDEN);
    }

    private ApiError buildApiError(String description, String message, String type) {
        return ApiError.builder()
                .errorDescription(description)
                .errorMessage(message)
                .errorType(type)
                .build();
    }
}
