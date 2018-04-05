package com.skyforce.goal.exception;

public class InvalidFileException extends Exception {
    private static final long SERIAL_VERSION_UID = 1L;

    public InvalidFileException(String message) {
        super(message);
    }
}
