package com.erp.backend.exceptions.advice;

import com.erp.backend.exceptions.ExitException;
import com.erp.backend.exceptions.ResourceNotFoundException;
import com.erp.backend.messages.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler{


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionMessage handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return new ExceptionMessage(400, "Invalid argument", errorMap);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionMessage processRuntimeException(RuntimeException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return new ExceptionMessage(500, "An internal server error occurred.", errorMap);
    }

    @ExceptionHandler(ExitException.class)
    public ExceptionMessage processSocialException(ExitException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        return new ExceptionMessage(e.getStatus().value(), e.getStatus().getReasonPhrase(), errorMap);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionMessage processResourceNotFoundException(ResourceNotFoundException e) {
        return e.getExceptionMessage();
    }



}
