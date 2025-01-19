package com.api.pharma.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.api.pharma.model.enums.Permission.*;

public enum Role {

    PHARMACIST(Set.of(CREATE_MEDICATION, CREATE_CONTROLLED_MEDICATION, CREATE_REVENUE, CREATE_ORDER, CREATE_INVOICE,
                      CREATE_USER, CREATE_ROLE,  CREATE_PHARMACY,
                      READ_MEDICATION, READ_CONTROLLED_MEDICATION, READ_REVENUE, READ_ORDER, READ_INVOICE, READ_USER,
                      READ_ROLE, READ_PHARMACY,
                      UPDATE_MEDICATION, UPDATE_CONTROLLED_MEDICATION, UPDATE_REVENUE, UPDATE_ORDER, UPDATE_INVOICE,
                      UPDATE_USER, UPDATE_ROLE,UPDATE_PHARMACY,
                      DELETE_MEDICATION, DELETE_CONTROLLED_MEDICATION, DELETE_REVENUE, DELETE_ORDER,
                      SEND_NOTIFICATIONS)),
    CUSTOMER(Set.of(CREATE_ORDER, CREATE_USER,
                    READ_ORDER, READ_MEDICATION, READ_CONTROLLED_MEDICATION, READ_REVENUE, READ_INVOICE)),
    ATTENDANT(Set.of(CREATE_ORDER, READ_ORDER, UPDATE_ORDER, DELETE_ORDER,
                     READ_MEDICATION, READ_CONTROLLED_MEDICATION, READ_REVENUE, READ_INVOICE,
                     CREATE_USER, READ_USER, UPDATE_USER,
                     PROCESS_PAYMENT, GENERATE_FINANCIAL_REPORT,
                     MANAGE_CUSTOMERS, SEND_NOTIFICATIONS, GENERATE_SALES_REPORT)),
    COMPOUNDER(Set.of(CREATE_MEDICATION, CREATE_CONTROLLED_MEDICATION, READ_MEDICATION, READ_CONTROLLED_MEDICATION,
                      APPROVE_FORMULA, REJECT_FORMULA,
                      MANAGE_STOCK, REQUEST_REORDER, GENERATE_INVENTORY_REPORT)),

    MANAGER (Set.of(Permission.values()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

   public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = permissions.stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                            .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
   }
}
