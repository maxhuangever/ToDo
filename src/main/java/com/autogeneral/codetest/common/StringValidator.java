package com.autogeneral.codetest.common;

import com.autoGeneral.codeTest.model.rest.ToDoItemValidationError;
import com.autoGeneral.codeTest.model.rest.ToDoItemValidationErrorDetails;
import com.autogeneral.codetest.exception.ToDoItemValidationException;
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

    public void validateBracketsStr(String str){
        validateStr("input", str, bracketsStrMinLength, bracketsStrMaxLength);
    }

    public void validateToDoItemText(String str){
        validateStr("text", str, textMinLength, textMaxLength);
    }

    private void validateStr(String paramName, String input, int minLength, int maxLength) {
        if (input == null || input.length() < minLength || input.length() > maxLength) {
            ToDoItemValidationErrorDetails errorDetails = new ToDoItemValidationErrorDetails();
            errorDetails.setLocation("params");
            errorDetails.setParam(paramName);
            errorDetails.setMsg("Must be between "+ minLength +" and "+ maxLength +" chars long");
            errorDetails.setValue(input);

            List<ToDoItemValidationErrorDetails> detailsList = new ArrayList<>();
            detailsList.add(errorDetails);

            ToDoItemValidationError error = new ToDoItemValidationError();
            error.setDetails(detailsList);

            throw new ToDoItemValidationException(error);
        }
    }
}
