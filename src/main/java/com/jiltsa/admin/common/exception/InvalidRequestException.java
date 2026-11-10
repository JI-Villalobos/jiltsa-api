package com.jiltsa.admin.common.exception;

/** The request is well-formed but asks for something the application rules do not allow (400). */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
