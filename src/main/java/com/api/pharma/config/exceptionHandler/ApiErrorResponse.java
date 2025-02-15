package com.api.pharma.config.exceptionHandler;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String title,
        int status,
        String detail,
        String instance,
        String method,
        String errorId,
        LocalDateTime timestamp) {
}
