package com.skyforce.goal.security.role;

public enum UserRole {
    ADMIN(1),
    MODERATOR(2),
    USER(3);

    private final int value;

    UserRole(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
