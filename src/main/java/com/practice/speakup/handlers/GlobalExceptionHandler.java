package com.practice.speakup.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getFieldErrors().forEach(fieldError -> {
            errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>())
                    .add(fieldError.getDefaultMessage());
        });

        String message = "There are validation errors.";

        if (!errors.isEmpty()) {
            message = "The " + String.join(", ", errors.keySet()) + " field" + (errors.size() > 1 ? "s are" : " is") + " invalid.";
        }

        Map<String, Object> result = new HashMap<>();
        result.put("status", HttpStatus.BAD_REQUEST.name());
        result.put("status_code", HttpStatus.BAD_REQUEST.value());
        result.put("message", message);
        result.put("details", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
