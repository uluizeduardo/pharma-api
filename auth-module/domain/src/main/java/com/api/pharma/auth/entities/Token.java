package com.api.pharma.auth.entities;

import com.api.pharma.auth.enums.TokenType;
import com.api.pharma.auth.valueobjects.TokenId;
import com.api.pharma.auth.valueobjects.UserId;

import java.util.Objects;

public class Token {

    private final TokenId id;

    private final String tokenValue;

    private final TokenType tokenType;

    private final boolean revoked;

    private final boolean expired;

    private final UserId userId;

    public Token (TokenId id, String tokenValue, TokenType tokenType, boolean revoked, boolean expired, UserId userId) {
        this.id = Objects.requireNonNull(id, "Token ID cannot be null");
        this.tokenValue = Objects.requireNonNull(tokenValue, "Token value cannot be null");
        this.tokenType = Objects.requireNonNull(tokenType, "Token type cannot be null");
        this.revoked = revoked;
        this.expired = expired;
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
    }

    public boolean isValid() {
        return !revoked && !expired;
    }
}
