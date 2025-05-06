package com.api.pharma.valueobjects;

import com.api.pharma.enums.Role;
import com.api.pharma.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserState(UUID id,
                        String userName,
                        String email,
                        Role role,
                        UserStatus status,
                        Instant createdAt,
                        UUID createdBy) {
}
