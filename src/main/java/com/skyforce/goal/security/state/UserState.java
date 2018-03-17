package com.skyforce.goal.security.state;

public enum UserState {
    ACTIVE(1),
    BANNED(2),
    NOT_ACTIVE(3);

    private final int value;

    UserState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
