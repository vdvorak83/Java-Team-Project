package com.skyforce.goal.security.state;

public enum UserState {
    EMPTY(0),
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
