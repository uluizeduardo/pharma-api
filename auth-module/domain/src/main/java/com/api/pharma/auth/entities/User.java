package com.api.pharma.auth.entities;

import com.api.pharma.auth.valueobjects.*;
import com.api.pharma.auth.valueobjects.password.Password;

import java.util.Objects;

public class User {
    private UserId id;
    private String userName;
    private Email email;
    private Password password;
    private Role role;
    private UserStatus status;
    private AuditInfo auditInfo;

    public User(UserId id, String userName, Email email, Password password, Role role, UserStatus status, AuditInfo auditInfo) {
        this.id = Objects.requireNonNull(id, "User ID cannot be null");
        this.userName = validateUserName(userName);
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.password = Objects.requireNonNull(password, "Password cannot be null");
        this.role = Objects.requireNonNull(role, "Role cannot be null");
        this.status = Objects.requireNonNull(status, "User status cannot be null");
        this.auditInfo = auditInfo != null ? auditInfo : AuditInfo.now();
    }

    private String validateUserName(String userName) {
        if (userName == null || userName.length() < 3){
            throw new IllegalArgumentException("User name must be at least 3 characters long");
        }
        return userName.trim();
    }
}