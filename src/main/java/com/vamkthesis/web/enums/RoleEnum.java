package com.vamkthesis.web.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN(0),
    USER(1);

    private int role;

    RoleEnum(int role) {
        this.role = role;
    }
}
