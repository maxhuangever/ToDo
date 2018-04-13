package com.autogeneral.codetest.service;

import com.autogeneral.codetest.dao.ToDoRepository;
import com.autogeneral.codetest.exception.ToDoItemNotFoundException;
import com.autogeneral.codetest.model.ToDo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ToDoServiceTest {
    @TestConfiguration
    static class ToDoServiceTestContextConfiguration {
        @Bean
        public ToDoService toDoService() {
            return new ToDoService();
        }
    }

    @Autowired
    private ToDoService toDoService;

    @MockBean
    private ToDoRepository toDoRepository;

    private static int id = 1;
    private static String text = "test text";

    @Test
    public void testCreateToDo_givenToDoItem_thenDataCreated() {
        //given
        ToDo toDo = new ToDo(text);
        given(toDoRepository.save(toDo)).willReturn(toDo);

        //when
        ToDo result = toDoService.createTodo(toDo);

        //then
        Assert.assertEquals(result.getIsCompleted(), false);
        Assert.assertNotNull(result.getCreatedAt());
        Assert.assertEquals(result.getText(), text);
    }

    @Test
    public void testGetTodoById_givenValidId_thenDataFound() {
        //given
        ToDo toDo = new ToDo(text);
        toDo.setId(id);
        given(toDoRepository.findOne(toDo.getId())).willReturn(toDo);

        //when
        ToDo result = toDoService.getTodoById(id);

        //then
        Assert.assertEquals(result.getId().intValue(), id);
        Assert.assertEquals(result.getText(), text);
    }

    @Test(expected = ToDoItemNotFoundException.class)
    public void testGetTodoById_givenInvalidId_thenThrowException() {
        //given
        given(toDoRepository.findOne(id)).willReturn(null);

        //when
        toDoService.getTodoById(id);

        //then exception will throw
    }

    @Test
    public void testUploadTodo_givenValidId_thenDataUpdated() {
        //given
        ToDo param = new ToDo(text);
        param.setId(id);
        param.setIsCompleted(true);
        ToDo entity = new ToDo();
        entity.setId(id);
        given(toDoRepository.findOne(param.getId())).willReturn(entity);
        given(toDoRepository.save(entity)).willReturn(entity);

        //when
        ToDo result = toDoService.uploadTodo(param);

        //then
        Assert.assertEquals(result.getId().intValue(), id);
        Assert.assertEquals(result.getText(), text);
        Assert.assertEquals(result.getIsCompleted(), true);
    }

    @Test(expected = ToDoItemNotFoundException.class)
    public void testUploadTodo_givenInvalidId_thenThrowException() {
        //given
        ToDo param = new ToDo(text);
        param.setId(id);
        given(toDoRepository.findOne(param.getId())).willReturn(null);

        //when
        toDoService.uploadTodo(param);

        //then exception will throw
    }

}
