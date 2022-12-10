package com.mikarte.javasecurityjwt.controller;

import com.mikarte.javasecurityjwt.exception.ApiError;
import com.mikarte.javasecurityjwt.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Error Handler, has field:
 * {@link ErrorHandler#apiError}
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    /**Error information field*/
    private final ApiError apiError = new ApiError();

    /**
     * Handle MethodArgumentNotValidException
     * @param exception {@link MethodArgumentNotValidException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        apiError.setMessage(exception.getMessage());
        apiError.setStatus("BAD_REQUEST");
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.warn(String.valueOf(exception));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle EntityNotFoundException
     * @param exception {@link EntityNotFoundException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler
    public ResponseEntity<?> handleNotFoundException(final EntityNotFoundException exception) {
        apiError.setMessage(exception.getMessage());
        apiError.setStatus("NOT_FOUND");
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.warn(String.valueOf(exception));
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle SQLException
     * @param exception {@link SQLException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler
    public ResponseEntity<?> handleSqlException(final SQLException exception) {
        apiError.setMessage(exception.getMessage());
        apiError.setStatus("CONFLICT");
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.warn(String.valueOf(exception));
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    /**
     * Handle ForbiddenException
     * @param exception {@link ForbiddenException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler
    public ResponseEntity<?> handleForbiddenExceptions(final ForbiddenException exception) {
        apiError.setMessage(exception.getMessage());
        apiError.setStatus("FORBIDDEN");
        apiError.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.warn(String.valueOf(exception));
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}