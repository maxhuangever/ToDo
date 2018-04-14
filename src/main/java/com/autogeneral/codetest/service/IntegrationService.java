package com.autogeneral.codetest.service;

import com.autogeneral.codeTest.model.rest.*;
import com.autogeneral.codetest.model.ToDo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class IntegrationService {
    private static RestTemplate restTemplate = new RestTemplate();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static HttpHeaders requestHeader = new HttpHeaders();

    static {
        requestHeader.setContentType(MediaType.APPLICATION_JSON);
    }

    private static ToDo expected = new ToDo();
    private static String toDoText = "sample text";

    static {
        expected.setText(toDoText);
        expected.setIsCompleted(false);
        expected.setCreatedAt(Calendar.getInstance().getTime());
    }

    public Object test(String baseUrl) throws JsonProcessingException {
        IntegrationTestResult testResult = new IntegrationTestResult();

        ToDoTestResult toDoTestResult = testCreateToDo(baseUrl);
        testResult.addTodoItem(toDoTestResult);

        int paramId = testResult.getTodo().get(0).getResult().getId().intValue();
        toDoTestResult = testGetToDo(baseUrl, paramId);
        testResult.addTodoItem(toDoTestResult);

        toDoTestResult = testUpdateToDo(baseUrl, paramId);
        testResult.addTodoItem(toDoTestResult);

        BracersTestResult bracersTestResult = testValidataBrackets(baseUrl, "([])", true);
        testResult.addBracersItem(bracersTestResult);
        bracersTestResult = testValidataBrackets(baseUrl, "([)]", false);
        testResult.addBracersItem(bracersTestResult);

        summarize(testResult);

        return testResult;
    }

    private ToDoTestResult testCreateToDo(String baseUrl) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("text", toDoText);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody), requestHeader);

        String endpoint = baseUrl + "/todo";
        ToDo toDo = restTemplate.postForObject(endpoint, entity, ToDo.class);

        expected.setId(toDo.getId());
        ToDoTestResult result = new ToDoTestResult();
        result.setInput("POST " + endpoint);
        result.setIsCorrect(toDo.equals(expected));
        result.setResult(transform(toDo));
        result.setExpected(transform(expected));

        return result;
    }

    private ToDoTestResult testGetToDo(String baseUrl, int id) throws JsonProcessingException {
        String endpoint = baseUrl + "/todo/" + id;
        ToDo toDo = restTemplate.getForObject(endpoint, ToDo.class);

        expected.setId(id);
        ToDoTestResult result = new ToDoTestResult();
        result.setInput("GET " + endpoint);
        result.setIsCorrect(toDo.equals(expected));
        result.setResult(transform(toDo));
        result.setExpected(transform(expected));

        return result;
    }

    private ToDoTestResult testUpdateToDo(String baseUrl, int id) throws JsonProcessingException {
        String endpoint = baseUrl + "/todo/" + id;
        ToDo expected = new ToDo();
        expected.setId(id);
        expected.setText("new text");
        expected.setIsCompleted(true);
        expected.setCreatedAt(Calendar.getInstance().getTime());

        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(expected), requestHeader);

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        ToDo toDo = restTemplate.patchForObject(endpoint, entity, ToDo.class);

        ToDoTestResult result = new ToDoTestResult();
        result.setInput("PATCH " + endpoint);
        result.setIsCorrect(toDo.equals(expected));
        result.setResult(transform(toDo));
        result.setExpected(transform(expected));

        return result;
    }

    private BracersTestResult testValidataBrackets(String baseUrl, String inputString, boolean expected) {
        String endpoint = baseUrl + "/tasks/validateBrackets?input={input}";
        Map<String, String> params = new HashMap<>();
        params.put("input", inputString);
        BalanceTestResult balanceTestResult = restTemplate.getForObject(endpoint, BalanceTestResult.class, params);

        BracersTestResult result = new BracersTestResult();
        result.setInput("GET " + "/tasks/validateBrackets?input=" + inputString);
        result.setIsCorrect(balanceTestResult.isIsBalanced() == expected);
        result.setResult(balanceTestResult.isIsBalanced());
        result.setExpected(expected);

        return result;
    }

    private ToDoItem transform(ToDo toDo) {
        ToDoItem item = new ToDoItem();
        item.setId(new BigDecimal(toDo.getId()));
        item.setText(toDo.getText());
        item.setIsCompleted(toDo.getIsCompleted());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        item.setCreatedAt(sdf.format(toDo.getCreatedAt()));

        return item;
    }

    private void summarize(IntegrationTestResult testResult) {
        boolean isCorrect = true;
        boolean existToDoError = testResult.getTodo().stream().anyMatch(result -> !result.isIsCorrect());
        if (!existToDoError) {
            isCorrect = testResult.getBracers().stream().allMatch(result -> result.isIsCorrect());
        }
        testResult.setIsCorrect(isCorrect);
    }
}
