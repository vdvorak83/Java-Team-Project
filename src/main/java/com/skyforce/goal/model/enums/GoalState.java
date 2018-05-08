package com.skyforce.goal.model.enums;

public enum GoalState {
    INPROGRESS(1),
    COMPLETED(2),
    FAILED(3),
    DRAFT(4);

    private final int value;

    GoalState(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
