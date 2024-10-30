package com.mewcare.meow_care_service.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SITTER("ROLE_SITTER"),
    ROLE_OWNER("ROLE_OWNER");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

}
