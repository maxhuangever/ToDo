package com.autogeneral.codetest.controller;

import com.autogeneral.codetest.exception.ToDoItemNotFoundException;
import com.autogeneral.codetest.exception.ToDoItemValidationException;
import com.autogeneral.codetest.model.rest.ToDoItemNotFoundError;
import com.autogeneral.codetest.model.rest.ToDoItemValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(ToDoItemValidationException.class)
    ResponseEntity<ToDoItemValidationError> handleToDoItemValidationException(ToDoItemValidationException ex) {
        return new ResponseEntity<ToDoItemValidationError>(
                ex.getToDoItemValidationError(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ToDoItemNotFoundException.class)
    ResponseEntity<ToDoItemNotFoundError> handleToDoItemNotFoundException(ToDoItemNotFoundException ex) {
        return new ResponseEntity<ToDoItemNotFoundError>(
                ex.getToDoItemNotFoundError(),
                HttpStatus.NOT_FOUND
        );
    }
}
