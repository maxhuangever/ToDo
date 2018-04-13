package com.autogeneral.codetest.exception;

import com.autoGeneral.codeTest.model.rest.ToDoItemNotFoundError;
import com.autoGeneral.codeTest.model.rest.ToDoItemNotFoundErrorDetails;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemNotFoundException extends BaseException {
    private ToDoItemNotFoundError toDoItemNotFoundError;

    public ToDoItemNotFoundException(String message) {
        super();

        toDoItemNotFoundError = new ToDoItemNotFoundError();

        ToDoItemNotFoundErrorDetails errorDetails = new ToDoItemNotFoundErrorDetails();
        errorDetails.setMessage(message);
        List<ToDoItemNotFoundErrorDetails> detailsList = new ArrayList<>();
        detailsList.add(errorDetails);
        toDoItemNotFoundError.setDetails(detailsList);

        String errorName = toDoItemNotFoundError.getName();
        if (errorName == null || errorName.trim().length() == 0) {
            toDoItemNotFoundError.setName("NotFoundError");
        }
    }

    public ToDoItemNotFoundError getToDoItemNotFoundError() {
        return toDoItemNotFoundError;
    }

    public void setToDoItemNotFoundError(ToDoItemNotFoundError toDoItemNotFoundError) {
        this.toDoItemNotFoundError = toDoItemNotFoundError;
    }
}
