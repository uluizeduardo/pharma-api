package com.api.pharma.modules.auth_module.domain.valueobjects;

import com.api.pharma.modules.auth_module.domain.enums.TokenType;

import java.time.Instant;
import java.util.UUID;

public record TokenState (UUID id, String tokenValue, TokenType tokenType, boolean revoked, boolean expired, UUID userId, Instant createdAt) {
}
