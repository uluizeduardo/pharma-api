package com.api.pharma.modules.auth_module.domain.enums;

/**
 * Enum representing various permissions in the system.
 *
 * Each permission is represented by a string identifier.
 * This enum can be used to manage user access control and authorization.
 */
public enum Permission {

    // Create
    CREATE_MEDICATION("create:medication"),
    CREATE_CONTROLLED_MEDICATION("create:controlled:medication"),
    CREATE_ORDER("create:order"),
    CREATE_REVENUE("create:revenue"),
    CREATE_INVOICE("create:invoice"),
    CREATE_USER("create:user"),
    CREATE_ROLE("create:role"),
    CREATE_PHARMACY("create:pharmacy"),

    // Read
    READ_MEDICATION("read:medication"),
    READ_CONTROLLED_MEDICATION("read:controlled:medication"),
    READ_ORDER("read:order"),
    READ_REVENUE("read:revenue"),
    READ_INVOICE("read:invoice"),
    READ_USER("read:user"),
    READ_ROLE("read:role"),
    READ_PHARMACY("read:pharmacy"),

    // Update
    UPDATE_MEDICATION("update:medication"),
    UPDATE_CONTROLLED_MEDICATION("update:controlled:medication"),
    UPDATE_ORDER("update:order"),
    UPDATE_REVENUE("update:revenue"),
    UPDATE_INVOICE("update:invoice"),
    UPDATE_USER("update:user"),
    UPDATE_ROLE("update:role"),
    UPDATE_PHARMACY("update:pharmacy"),

    // Delete
    DELETE_MEDICATION("delete:medication"),
    DELETE_CONTROLLED_MEDICATION("delete:controlled:medication"),
    DELETE_ORDER("delete:order"),
    DELETE_REVENUE("delete:revenue"),
    DELETE_INVOICE("delete:invoice"),
    DELETE_USER("delete:user"),
    DELETE_ROLE("delete:role"),
    DELETE_PHARMACY("delete:pharmacy"),

    // Report Generation
    GENERATE_SALES_REPORT("generate:report:sales"),
    GENERATE_INVENTORY_REPORT("generate:report:inventory"),
    GENERATE_CUSTOMER_REPORT("generate:report:customer"),

    // Configuration
    MANAGE_SYSTEM_SETTINGS("manage:settings:system"),
    MANAGE_PRICE_LIST("manage:settings:pricelist"),
    MANAGE_TAXES("manage:settings:taxes"),

    // Quality Control
    APPROVE_FORMULA("control:quality:approve:formula"),
    REJECT_FORMULA("control:quality:reject:formula"),
    REVIEW_BATCH("control:quality:review:batch"),

    // Finance
    PROCESS_PAYMENT("finance:process:payment"),
    GENERATE_FINANCIAL_REPORT("finance:generate:report"),

    // Stock Management
    MANAGE_STOCK("stock:manage"),
    REQUEST_REORDER("stock:request:reorder"),

    // Customer Management
    MANAGE_CUSTOMERS("customers:manage"),
    SEND_NOTIFICATIONS("customers:send:notifications");

    private final String authority;

    Permission(String permission) {
        this.authority = permission;
    }

    public String getAuthority() {
        return authority;
    }
}
