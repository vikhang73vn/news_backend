package com.erp.backend.exceptions;

import com.erp.backend.messages.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private ExceptionMessage exceptionMessage;
    private String message;
    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
        this.exceptionMessage = new ExceptionMessage(HttpStatus.NOT_FOUND.value(), message,new HashMap<>());
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }
}
