package com.autogeneral.codetest.common;

import com.autogeneral.codetest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.model.rest.ToDoItemValidationErrorDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class StringValidator {
    @NotNull
    @Value("${ValidateBrackets.input.minLength}")
    private int bracketsStrMinLength;
    @NotNull
    @Value("${ValidateBrackets.input.maxLength}")
    private int bracketsStrMaxLength;

    @NotNull
    @Value("${ToDoItem.text.minLength}")
    private int textMinLength;
    @NotNull
    @Value("${ToDoItem.text.maxLength}")
    private int textMaxLength;

    public ToDoItemValidationError validateBracketsStr(String str) {
        return validateStr("input", str, bracketsStrMinLength, bracketsStrMaxLength);
    }

    public ToDoItemValidationError validateToDoItemText(String str) {
        return validateStr("text", str, textMinLength, textMaxLength);
    }

    private ToDoItemValidationError validateStr(String paramName, String input, int minLength, int maxLength) {
        if (input == null || input.length() < minLength || input.length() > maxLength) {
            ToDoItemValidationErrorDetails errorDetails = new ToDoItemValidationErrorDetails();
            errorDetails.setLocation("params");
            errorDetails.setParam(paramName);
            errorDetails.setMsg("Must be between " + minLength + " and " + maxLength + " chars long");
            errorDetails.setValue(input);

            List<ToDoItemValidationErrorDetails> detailsList = new ArrayList<>();
            detailsList.add(errorDetails);

            ToDoItemValidationError error = new ToDoItemValidationError();
            error.setDetails(detailsList);

            return error;
        }
        return null;
    }

    public void setBracketsStrMinLength(int bracketsStrMinLength) {
        this.bracketsStrMinLength = bracketsStrMinLength;
    }

    public void setBracketsStrMaxLength(int bracketsStrMaxLength) {
        this.bracketsStrMaxLength = bracketsStrMaxLength;
    }

    public void setTextMinLength(int textMinLength) {
        this.textMinLength = textMinLength;
    }

    public void setTextMaxLength(int textMaxLength) {
        this.textMaxLength = textMaxLength;
    }
}
