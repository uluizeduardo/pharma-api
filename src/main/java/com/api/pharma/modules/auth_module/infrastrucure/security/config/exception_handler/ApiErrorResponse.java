package com.api.pharma.modules.auth_module.infrastrucure.security.config.exception_handler;

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
