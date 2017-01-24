package com.example.room.chat.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by igorek2312 on 23.01.17.
 */
public enum Role implements GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
