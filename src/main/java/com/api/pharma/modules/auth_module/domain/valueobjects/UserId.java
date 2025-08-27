package com.api.pharma.modules.auth_module.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a unique identifier for a user in the system.
 *
 * This Value Object is immutable and encapsulates the user ID as a UUID.
 */
public class UserId {

    private final UUID value;

    public UserId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId that = (UserId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
