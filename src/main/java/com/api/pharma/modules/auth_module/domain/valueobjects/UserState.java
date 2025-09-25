package com.api.pharma.modules.auth_module.domain.valueobjects;

import com.api.pharma.modules.auth_module.domain.enums.Role;
import com.api.pharma.modules.auth_module.domain.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserState(UUID id,
                        String userName,
                        String email,
                        String password,
                        Role role,
                        UserStatus status,
                        Instant createdAt,
                        UUID createdBy) {
}
