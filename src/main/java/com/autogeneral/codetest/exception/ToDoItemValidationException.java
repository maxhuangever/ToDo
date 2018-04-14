package com.autogeneral.codetest.exception;

import com.autoGeneral.codeTest.model.rest.ToDoItemValidationError;

public class ToDoItemValidationException extends BaseException {
    private static String DEFAULT_ERROR_NAME= "ValidationError";

    private ToDoItemValidationError toDoItemValidationError;

    public ToDoItemValidationException(ToDoItemValidationError error) {
        super();
        toDoItemValidationError = error;
        String errorName = toDoItemValidationError.getName();
        if (errorName == null || errorName.trim().length() == 0) {
            toDoItemValidationError.setName(DEFAULT_ERROR_NAME);
        }
    }

    public ToDoItemValidationError getToDoItemValidationError() {
        return toDoItemValidationError;
    }

    public void setToDoItemValidationError(ToDoItemValidationError toDoItemValidationError) {
        this.toDoItemValidationError = toDoItemValidationError;
    }
}
