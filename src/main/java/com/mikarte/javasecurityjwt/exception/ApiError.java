package com.mikarte.javasecurityjwt.exception;

import lombok.Data;

/**
 * Error information class, contains fields:
 * {@link ApiError#message},
 * {@link ApiError#status},
 * {@link ApiError#timestamp}
 */
@Data
public class ApiError {
    /**Event about error*/
    private String message;
    /**Http status of error*/
    private String status;
    /**Date and time of occurrence*/
    private String timestamp;
}
