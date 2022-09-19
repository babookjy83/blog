package com.blog.search.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(BindException e) {
    	final String errorMessage = e.getAllErrors().stream().findFirst().get().getDefaultMessage();
    	
    	final Map<String, String> body = new HashMap<>();
		body.put("error_code", "4000");
		body.put("error_message", errorMessage);
		
        return ResponseEntity.ok().body(body);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, String>> applicationExceptions(ApplicationException e) {
    	final Map<String, String> body = new HashMap<>();
		body.put("error_code", e.getCode());
		body.put("error_message", e.getMessage());
		
        return ResponseEntity.ok().body(body);
    }
}
