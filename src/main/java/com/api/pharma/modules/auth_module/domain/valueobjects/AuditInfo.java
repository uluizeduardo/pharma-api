package com.api.pharma.modules.auth_module.domain.valueobjects;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents metadata for auditing domain entities, capturing creation and last modification timestamps.
 *
 * This Value Object is immutable and used to track entity lifecycle events like creation and updates.
 */
public class AuditInfo {

    private final Instant createdAt;
    private final Instant updatedAt;
    private final UserId createdBy;
    private final UserId updatedBy;

    public AuditInfo(Instant createdAt, Instant updatedAt, UserId createdBy, UserId updatedBy) {
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public static AuditInfo createdBy(UserId createdBy) {
        Instant now = Instant.now();
        return new AuditInfo(now, now, createdBy, createdBy);
    }

    public static AuditInfo selfCreated() {
        Instant now = Instant.now();
        return new AuditInfo(now, now, null, null);
    }

    public AuditInfo updatedBy(UserId updatedBy) {
        return new AuditInfo(this.createdAt, Instant.now(), this.createdBy, updatedBy);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UUID getCreatedBy() {
        return createdBy != null ? createdBy.getValue() : null;
    }

    public UUID getUpdatedBy() {
        return updatedBy != null ? updatedBy.getValue() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditInfo)) return false;
        AuditInfo that = (AuditInfo) o;
        return createdAt.equals(that.createdAt) && updatedAt.equals(that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "AuditInfo{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
