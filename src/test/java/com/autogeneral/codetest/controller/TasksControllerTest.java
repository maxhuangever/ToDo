package com.autogeneral.codetest.controller;

import com.autogeneral.codeTest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.common.StringValidator;
import com.autogeneral.codetest.service.ValidateBracketsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TasksController.class)
public class TasksControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StringValidator stringValidator;

    @MockBean
    private ValidateBracketsService validateBracketsService;

    @Test
    public void givenValidBracketString_whenValidateBrackets_thenReturnTrue()
            throws Exception {
        //given
        String inputString = "[]";
        given(stringValidator.validateBracketsStr(inputString)).willReturn(null);
        given(validateBracketsService.checkBalance(inputString)).willReturn(true);

        //when
        mvc.perform(get("/tasks/validateBrackets").param("input", inputString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.input", is(inputString)))
                .andExpect(jsonPath("$.isBalanced", is(true)));
    }

    @Test
    public void givenInvalidBracketString_whenValidateBrackets_thenReturnFalse()
            throws Exception {
        //given
        String inputString = "][";
        given(stringValidator.validateBracketsStr(inputString)).willReturn(null);
        given(validateBracketsService.checkBalance(inputString)).willReturn(false);

        //when
        mvc.perform(get("/tasks/validateBrackets").param("input", inputString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.input", is(inputString)))
                .andExpect(jsonPath("$.isBalanced", is(false)));
    }

    @Test
    public void givenInvalidInut_whenValidateBrackets_thenReturnError()
            throws Exception {
        //given
        String inputString = "";
        ToDoItemValidationError error = new ToDoItemValidationError();
        given(stringValidator.validateBracketsStr(inputString)).willReturn(error);

        //when
        mvc.perform(get("/tasks/validateBrackets").param("input", inputString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
