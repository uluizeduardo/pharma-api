package com.api.pharma.modules.auth_module.infrastrucure.security.config.exception_handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class AlertException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;
}
