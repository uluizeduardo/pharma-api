package com.api.pharma.config.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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

    @ExceptionHandler(AlertException.class)
    public ResponseEntity<?> alertExceptionHandler(
            final AlertException exception,
            final HttpServletRequest request) {

        var errorId = UUID.randomUUID().toString();
        log.error(String.format("Error UUID= %s error message: %s", errorId, exception.getMessage()), exception);

        var response = new ApiErrorResponse(
                exception.getHttpStatus().getReasonPhrase(),                        // `title`
                exception.getHttpStatus().value(),                                  // `status`
                exception.getMessage(),                                             // `detail`
                request.getRequestURI(),                                            // `instance`
                request.getMethod(),                                                // `method`
                errorId,                                                            // `errorId`
                LocalDateTime.now()                                                 // `timestamp`
        );

        return new ResponseEntity<>(response, exception.getHttpStatus());

    }
}
