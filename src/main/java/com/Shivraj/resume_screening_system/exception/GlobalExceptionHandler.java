package com.Shivraj.resume_screening_system.exception;

import com.Shivraj.resume_screening_system.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {

        ApiResponse response =
                new ApiResponse(false, ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
