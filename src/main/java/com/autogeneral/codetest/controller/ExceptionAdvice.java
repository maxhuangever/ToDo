package com.autogeneral.codetest.controller;

import com.autoGeneral.codeTest.model.rest.ToDoItemNotFoundError;
import com.autoGeneral.codeTest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.exception.ToDoItemNotFoundException;
import com.autogeneral.codetest.exception.ToDoItemValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice{
    @ExceptionHandler(Exception.class)
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
