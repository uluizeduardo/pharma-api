package com.api.pharma.modules.auth_module.infrastrucure.security.config.exception_handler;

import com.api.pharma.modules.auth_module.application.exceptions.UserException;
import com.api.pharma.modules.auth_module.application.exceptions.constants.UserMessages;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlertExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(
            String message,
            HttpStatus status,
            HttpServletRequest request
    ) {
        var errorId = UUID.randomUUID().toString();
        log.error("Error UUID= %s error message: %s", errorId, message);

        var response = new ApiErrorResponse(
                status.getReasonPhrase(),
                status.value(),
                message,
                request.getRequestURI(),
                request.getMethod(),
                errorId,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(AlertException.class)
    public ResponseEntity<?> alertExceptionHandler(
            final AlertException exception,
            final HttpServletRequest request) {

        return buildErrorResponse(exception.getMessage(), exception.getHttpStatus(), request);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleUserException(
            UserException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = switch (ex.getMessage()) {
            case UserMessages.USER_NOT_FOUND, UserMessages.USER_NOT_FOUND_BY_EMAIL ->  HttpStatus.NOT_FOUND;
            case UserMessages.EMAIL_ALREADY_IN_USE -> HttpStatus.CONFLICT;
            case UserMessages.ACCOUNT_DISABLED, UserMessages.ACCOUNT_LOCKED -> HttpStatus.FORBIDDEN;
            case UserMessages.INVALID_CREDENTIALS, UserMessages.AUTHENTICATION_FAILED -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return buildErrorResponse(ex.getMessage(), status, request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErrorResponse> handleNullPointerException(
            NullPointerException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handlerRuntimeException(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

}
