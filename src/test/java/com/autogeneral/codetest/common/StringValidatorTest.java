package com.autogeneral.codetest.common;

import com.autogeneral.codeTest.model.rest.ToDoItemValidationError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class StringValidatorTest {
    private StringValidator stringValidator = new StringValidator();

    @Parameterized.Parameters(name = "{index}: validation of \"{0}\" will be {1}")
    public static Object[] invalidData() {
        return new Object[][]{
                {"", false},
                {"this string has more than 50 chars.1234567890asdfghjklzxcvbnm,./;'", false},
                {null, false},
                {"wfs'fs[{{{(s;dkls(dslkf)s;dlkf}]}]}}}sd", true}
        };
    }

    @Parameterized.Parameter(0)
    public String inputStr;

    @Parameterized.Parameter(1)
    public boolean validationResult;

    @Before
    public void setUp(){
        stringValidator.setBracketsStrMinLength(1);
        stringValidator.setBracketsStrMaxLength(50);
        stringValidator.setTextMinLength(1);
        stringValidator.setTextMaxLength(50);
    }

    @Test
    public void testValidateToDoItemText_givenInvalidString_thenThrowException() {
        //given parameters

        //when
        ToDoItemValidationError error = stringValidator.validateToDoItemText(inputStr);

        //then
        Assert.assertTrue((error == null) == validationResult);
    }

    @Test
    public void testValidateBracketsStr_givenInvalidString_thenThrowException() {
        //given parameters

        //when
        ToDoItemValidationError error = stringValidator.validateBracketsStr(inputStr);

        //then
        Assert.assertTrue((error == null) == validationResult);
    }
}
