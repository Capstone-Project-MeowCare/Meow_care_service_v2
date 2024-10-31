package com.mewcare.meow_care_service.enums;

public enum RoleName {
    USER("USER"),
    ADMIN("ADMIN"),
    SITTER("SITTER"),
    OWNER("OWNER"),
    MANAGER("MANAGER");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }
}
