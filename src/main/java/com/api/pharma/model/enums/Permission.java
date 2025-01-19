package com.api.pharma.model.enums;

public enum Permission {

    // Criação
    CREATE_MEDICATION("create:medication"),
    CREATE_CONTROLLED_MEDICATION("create:controlled:medication"),
    CREATE_ORDER("create:order"),
    CREATE_REVENUE("create:revenue"),
    CREATE_INVOICE("create:invoice"),
    CREATE_USER("create:user"),
    CREATE_ROLE("create:role"),
    CREATE_PHARMACY("create:pharmacy"),

    // Leitura
    READ_MEDICATION("read:medication"),
    READ_CONTROLLED_MEDICATION("read:controlled:medication"),
    READ_ORDER("read:order"),
    READ_REVENUE("read:revenue"),
    READ_INVOICE("read:invoice"),
    READ_USER("read:user"),
    READ_ROLE("read:role"),
    READ_PHARMACY("read:pharmacy"),

    // Atualização
    UPDATE_MEDICATION("update:medication"),
    UPDATE_CONTROLLED_MEDICATION("update:controlled:medication"),
    UPDATE_ORDER("update:order"),
    UPDATE_REVENUE("update:revenue"),
    UPDATE_INVOICE("update:invoice"),
    UPDATE_USER("update:user"),
    UPDATE_ROLE("update:role"),
    UPDATE_PHARMACY("update:pharmacy"),

    // Exclusão
    DELETE_MEDICATION("delete:medication"),
    DELETE_CONTROLLED_MEDICATION("delete:controlled:medication"),
    DELETE_ORDER("delete:order"),
    DELETE_REVENUE("delete:revenue"),
    DELETE_INVOICE("delete:invoice"),
    DELETE_USER("delete:user"),
    DELETE_ROLE("delete:role"),
    DELETE_PHARMACY("delete:pharmacy"),

    // Relatórios
    GENERATE_SALES_REPORT("generate:report:sales"),
    GENERATE_INVENTORY_REPORT("generate:report:inventory"),
    GENERATE_CUSTOMER_REPORT("generate:report:customer"),

    // Configurações
    MANAGE_SYSTEM_SETTINGS("manage:settings:system"),
    MANAGE_PRICE_LIST("manage:settings:pricelist"),
    MANAGE_TAXES("manage:settings:taxes"),

    // Controle de Qualidade
    APPROVE_FORMULA("control:quality:approve:formula"),
    REJECT_FORMULA("control:quality:reject:formula"),
    REVIEW_BATCH("control:quality:review:batch"),

    // Financeiro
    PROCESS_PAYMENT("finance:process:payment"),
    GENERATE_FINANCIAL_REPORT("finance:generate:report"),

    // Estoque
    MANAGE_STOCK("stock:manage"),
    REQUEST_REORDER("stock:request:reorder"),

    // Clientes
    MANAGE_CUSTOMERS("customers:manage"),
    SEND_NOTIFICATIONS("customers:send:notifications");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
