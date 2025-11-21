package com.example.taskmanager.exception;

import com.example.taskmanager.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntime(RuntimeException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, null, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAll(Exception ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, null, "Internal error: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
