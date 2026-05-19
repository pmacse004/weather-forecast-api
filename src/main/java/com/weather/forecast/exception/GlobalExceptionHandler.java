package com.weather.forecast.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global Exception Handler for the application.
 * This class handles all exceptions thrown by controllers and provides
 * consistent error responses to clients.
 * 
 * @RestControllerAdvice - Combines @ControllerAdvice and @ResponseBody
 *                         Allows handling exceptions across all controllers
 * 
 * @author Weather Forecast Team
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Logger for logging errors
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Handles RuntimeException thrown by the application
     * 
     * @ExceptionHandler - Specifies which exception this method handles
     * 
     * @param ex - The exception that was thrown
     * @param request - The web request that caused the exception
     * @return ResponseEntity - HTTP response with error details
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex, 
            WebRequest request) {
        
        logger.error("RuntimeException occurred: {}", ex.getMessage(), ex);
        
        // Create error response body
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handles IllegalArgumentException (e.g., invalid input parameters)
     * 
     * @param ex - The exception that was thrown
     * @param request - The web request that caused the exception
     * @return ResponseEntity - HTTP response with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex, 
            WebRequest request) {
        
        logger.error("IllegalArgumentException occurred: {}", ex.getMessage());
        
        // Create error response body
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handles all other exceptions not specifically handled above
     * This is a catch-all handler for unexpected errors
     * 
     * @param ex - The exception that was thrown
     * @param request - The web request that caused the exception
     * @return ResponseEntity - HTTP response with error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(
            Exception ex, 
            WebRequest request) {
        
        logger.error("Unexpected exception occurred: {}", ex.getMessage(), ex);
        
        // Create error response body
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "An unexpected error occurred. Please try again later.");
        body.put("path", request.getDescription(false).replace("uri=", ""));
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Made with Bob
