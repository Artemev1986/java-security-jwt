package com.mikarte.javasecurityjwt.exception;

/**
 * Error Failure to meet the conditions for the operation
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
