package com.autogeneral.codetest.controller;

import com.autogeneral.codetest.service.IntegrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationTestController {
    @Autowired
    private IntegrationService integrationService;

    @GetMapping(path = "/integrationTest")
    public Object toDoIntegrationTest(@RequestParam String url) throws JsonProcessingException {
        return integrationService.test(url);
    }
}
