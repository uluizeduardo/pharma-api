package com.api.pharma.entities;

import com.api.pharma.enums.Permission;
import com.api.pharma.enums.Role;
import com.api.pharma.enums.UserStatus;
import com.api.pharma.valueobjects.AuditInfo;
import com.api.pharma.valueobjects.Email;
import com.api.pharma.valueobjects.UserId;
import com.api.pharma.valueobjects.UserName;
import com.api.pharma.valueobjects.password.HashedPassword;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user in the system with various attributes and behaviors.
 *
 * This Entity is mutable and encapsulates user-related operations such as registration, permission checks, etc.
 */
public class User {
    private UserId id;
    private UserName userName;
    private Email email;
    private HashedPassword hashedPassword;
    private Role role;
    private UserStatus status;
    private AuditInfo auditInfo;

    public User(UserId id, UserName userName, Email email, HashedPassword hashedPassword, Role role, UserStatus status, AuditInfo auditInfo) {
        this.id = Objects.requireNonNull(id, "User ID cannot be null");
        this.userName = Objects.requireNonNull(userName);
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.hashedPassword = Objects.requireNonNull(hashedPassword, "Password cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
        this.status = Objects.requireNonNull(status, "User status cannot be null");
        this.auditInfo =Objects.requireNonNull(auditInfo, "Audit info must be provided explicitly");
    }

    public static User selfRegisterd(UserName userName, Email email, HashedPassword hashedPassword, Role role) {
        UserId id = new UserId(UUID.randomUUID());
        return new User(id, userName, email, hashedPassword, role, UserStatus.ACTIVE, AuditInfo.createdBy(id));
    }

    public static User createdByAdmin(UserName userName, Email email, HashedPassword hashedPassword, Role role, UserId adminId) {
        UserId id = new UserId(UUID.randomUUID());
        return new User(id, userName, email, hashedPassword, role, UserStatus.ACTIVE, AuditInfo.createdBy(adminId));
    }

    public boolean hasPermission(Permission permission) {
        return role.getPermissions().contains(permission);
    }
}