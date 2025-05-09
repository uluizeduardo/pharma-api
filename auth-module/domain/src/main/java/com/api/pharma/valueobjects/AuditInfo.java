package com.api.pharma.valueobjects;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents metadata for auditing domain entities, capturing creation and last modification timestamps.
 *
 * This Value Object is immutable and used to track entity lifecycle events like creation and updates.
 */
public class AuditInfo {

    private final Instant createdAt;
    private final Instant updatedAt;
    private final UserId createdBy;

    public AuditInfo(Instant createdAt, Instant updatedAt, UserId createdBy) {
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        this.createdBy = Objects.requireNonNull(createdBy, "createdBy must not be null");
    }

    public static AuditInfo createdBy(UserId createdBy) {
        Instant now = Instant.now();
        return new AuditInfo(now, now, createdBy);
    }

    public AuditInfo updatedBy(UserId createdBy) {
        return new AuditInfo(this.createdAt, Instant.now(), createdBy);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserId getCreatedBy() {
        return createdBy;
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
