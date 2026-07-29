package com.ankit.studentmanagement.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.error.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ankit.studentmanagement.dto.response.ErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StudentNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                List.of(),
                request.getRequestURI()
        );
        return
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(errorResponse);


    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex , HttpServletRequest request){
        var bindingResult = ex.getBindingResult();
        //var means automatically assign the data tyoe based on the ex.getBindingResult;
        List<FieldError> fieldError =bindingResult.getFieldErrors();
        List<String> errors = fieldError.stream()
//                .map(fieldError::getDefaultMessage)
//                .map(error -> error.getDefaultMessage())
                .map(FieldError::getDefaultMessage)
                .toList();
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Validation Failed",
                   errors,
                request.getRequestURI()
        );
        return
                ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse);


    }
}
