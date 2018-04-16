package com.autogeneral.codetest.controller;

import com.autogeneral.codetest.common.StringValidator;
import com.autogeneral.codetest.exception.ToDoItemNotFoundException;
import com.autogeneral.codetest.model.ToDo;
import com.autogeneral.codetest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.service.ToDoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ToDoService todoService;

    @MockBean
    private StringValidator stringValidator;

    private static long id = 1L;
    private static String text = "test text";

    @Test
    public void givenValidContent_whenCreateToDo_thenReturnCreatedData()
            throws Exception {
        //given
        ToDo toDo = new ToDo(text);
        given(stringValidator.validateToDoItemText(toDo.getText())).willReturn(null);
        given(todoService.createTodo(Matchers.any(ToDo.class))).willReturn(toDo);

        //when
        mvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(text)));
    }

    @Test
    public void givenValidContent_whenCreateToDo_thenReturn400Error()
            throws Exception {
        //given
        ToDo toDo = new ToDo("");
        ToDoItemValidationError error = new ToDoItemValidationError();
        given(stringValidator.validateToDoItemText(toDo.getText())).willReturn(error);

        //when
        mvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void givenValidId_whenGetTodo_thenReturnData()
            throws Exception {
        //given
        long id = 1L;
        ToDo toDo = new ToDo(text);
        given(todoService.getTodoById(id)).willReturn(toDo);

        //when
        mvc.perform(get("/todo/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(text)));
    }

    @Test
    public void givenInvalidId_whenGetTodo_thenReturn404Error()
            throws Exception {
        //given
        long id = 1L;
        given(todoService.getTodoById(id)).willThrow(ToDoItemNotFoundException.class);

        //when
        mvc.perform(get("/todo/" + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidContent_whenUpdateToDo_thenReturnUpdatedData()
            throws Exception {
        //given
        ToDo toDo = new ToDo(text);
        given(stringValidator.validateToDoItemText(toDo.getText())).willReturn(null);
        given(todoService.uploadTodo(Matchers.any(ToDo.class))).willReturn(toDo);

        //when
        mvc.perform(patch("/todo/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(text)));
    }

    @Test
    public void givenInvalidText_whenUpdateToDo_thenReturn400Error()
            throws Exception {
        //given
        ToDo toDo = new ToDo("");
        ToDoItemValidationError error = new ToDoItemValidationError();
        given(stringValidator.validateToDoItemText(toDo.getText())).willReturn(error);

        //when
        mvc.perform(patch("/todo/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidId_whenUpdateToDo_thenReturn404Error()
            throws Exception {
        //given
        ToDo toDo = new ToDo(text);
        given(stringValidator.validateToDoItemText(toDo.getText())).willReturn(null);
        given(todoService.uploadTodo(Matchers.any(ToDo.class))).willThrow(ToDoItemNotFoundException.class);

        //when
        mvc.perform(patch("/todo/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDo)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
