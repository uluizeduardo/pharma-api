package com.api.pharma.modules.auth_module.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class TokenId {

    private final UUID value;

    public TokenId(UUID value) {
        this.value = Objects.requireNonNull(value, "Token ID cannot be null");
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenId)) return false;
        TokenId tokenId = (TokenId) o;
        return value.equals(tokenId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
