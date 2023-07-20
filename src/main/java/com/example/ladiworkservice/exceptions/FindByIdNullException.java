package com.example.ladiworkservice.exceptions;

public class FindByIdNullException extends RuntimeException {
    public FindByIdNullException(String message) {
        super(message);
    }

    public FindByIdNullException(String message, Throwable cause) {
        super(message, cause);
    }

    protected FindByIdNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
