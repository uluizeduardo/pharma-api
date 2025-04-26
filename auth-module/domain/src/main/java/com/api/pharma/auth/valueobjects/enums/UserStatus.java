package com.api.pharma.auth.valueobjects.enums;

/**
 * Enum representing the status of a user in the system.
 *
 * This enum is used to indicate whether a user is active, inactive, or blocked.
 */
public enum UserStatus {

    ACTIVE,
    INACTIVE,
    BLOCKED;

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isInactive() {
        return this == INACTIVE;
    }

    public boolean isBlocked() {
        return this == BLOCKED;
    }
}
