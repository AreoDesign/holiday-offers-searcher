package com.home.ans.holidays.exception;

public class NoMatchingValueException extends RuntimeException {

    public NoMatchingValueException() {
    }

    public NoMatchingValueException(String message) {
        super(message);
    }
}
