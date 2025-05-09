package com.api.pharma.valueobjects;

import com.api.pharma.enums.TokenType;

import java.util.UUID;

public record TokenState (UUID id, String tokenValue, TokenType tokenType, boolean revoked, boolean expired, UUID userId) {
}
