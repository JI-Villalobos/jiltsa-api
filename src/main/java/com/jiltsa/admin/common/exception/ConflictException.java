package com.jiltsa.admin.common.exception;

/** The request would break a uniqueness rule of the stored data. */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
