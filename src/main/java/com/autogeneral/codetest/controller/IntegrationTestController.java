package com.autogeneral.codetest.controller;

import com.autoGeneral.codeTest.model.rest.BalanceTestResult;
import com.autoGeneral.codeTest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.exception.ToDoItemValidationException;
import com.autogeneral.codetest.service.IntegrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IntegrationTestController {
    @Autowired
    private IntegrationService integrationService;

    @GetMapping(path = "/integrationTest")
    public Object toDoIntegrationTest(@RequestParam String url) throws JsonProcessingException {
        return integrationService.test(url);
    }
}
