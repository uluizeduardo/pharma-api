package com.api.pharma.modules.auth_module.domain.entities;

import com.api.pharma.modules.auth_module.domain.enums.Permission;
import com.api.pharma.modules.auth_module.domain.enums.Role;
import com.api.pharma.modules.auth_module.domain.enums.UserStatus;
import com.api.pharma.modules.auth_module.domain.valueobjects.*;
import com.api.pharma.modules.auth_module.domain.valueobjects.Email;
import com.api.pharma.modules.auth_module.domain.valueobjects.password.HashedPassword;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Represents a user in the system with various attributes and behaviors.
 *
 * This Entity is mutable and encapsulates user-related operations such as registration, permission checks, etc.
 */
@Slf4j
public class User {
    private UserId id;
    private UserName userName;
    private Email email;
    private HashedPassword hashedPassword;
    private Role role;
    private UserStatus status;
    private AuditInfo auditInfo;

    public User(UserId id, UserName userName, Email email, HashedPassword hashedPassword, Role role, UserStatus status, AuditInfo auditInfo) {
        this.id = id;
        this.userName = Objects.requireNonNull(userName);
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.hashedPassword = Objects.requireNonNull(hashedPassword, "Password cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
        this.status = Objects.requireNonNull(status, "User status cannot be null");
        this.auditInfo =Objects.requireNonNull(auditInfo, "Audit info must be provided explicitly");
    }

    public User(UserName userName, Email email, HashedPassword hashedPassword, Role role, UserStatus status, AuditInfo auditInfo) {
        this.userName = Objects.requireNonNull(userName);
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.hashedPassword = Objects.requireNonNull(hashedPassword, "Password cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
        this.status = Objects.requireNonNull(status, "User status cannot be null");
        this.auditInfo =Objects.requireNonNull(auditInfo, "Audit info must be provided explicitly");
    }

    public static User selfRegistered(UserName userName, Email email, HashedPassword hashedPassword, Role role) {
        return new User(userName, email, hashedPassword, role, UserStatus.ACTIVE, AuditInfo.selfCreated());
    }

    public static User createdByAdmin(UserName userName, Email email, HashedPassword hashedPassword, Role role, UserId adminId) {
        return new User(userName, email, hashedPassword, role, UserStatus.ACTIVE, AuditInfo.createdBy(adminId));
    }

    public boolean hasPermission(Permission permission) {
        return role.getPermissions().contains(permission);
    }

    public UserState exportState() {
        return new UserState(
                id.getValue(),
                userName.getValue(),
                email.getValue(),
                hashedPassword.getValue(),
                role,
                status,
                auditInfo.getCreatedAt(),
                auditInfo.getCreatedBy()
        );
    }

    public UserState exportNewUserState() {
       return new UserState(
               null, // New users do not have an ID yet
               userName.getValue(),
               email.getValue(),
               hashedPassword.getValue(),
               role,
               status,
               auditInfo.getCreatedAt(),
               auditInfo.getCreatedBy()
       );
    }
}