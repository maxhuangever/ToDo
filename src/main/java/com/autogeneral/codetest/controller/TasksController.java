package com.autogeneral.codetest.controller;

import com.autogeneral.codeTest.model.rest.BalanceTestResult;
import com.autogeneral.codeTest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.common.StringValidator;
import com.autogeneral.codetest.exception.ToDoItemValidationException;
import com.autogeneral.codetest.service.ValidateBracketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TasksController {
    @Autowired
    private ValidateBracketsService validateBracketsService;

    @Autowired
    StringValidator stringValidator;

    @GetMapping(path = "/tasks/validateBrackets")
    public BalanceTestResult validateBrackets(@RequestParam String input) {
        ToDoItemValidationError error = stringValidator.validateBracketsStr(input);
        if (error != null) {
            throw new ToDoItemValidationException(error);
        }

        boolean isBalanced = validateBracketsService.checkBalance(input);

        BalanceTestResult result = new BalanceTestResult();
        result.input(input);
        result.isBalanced(isBalanced);
        return result;
    }
}
