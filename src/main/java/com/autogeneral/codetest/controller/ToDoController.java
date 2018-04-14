package com.autogeneral.codetest.controller;

import com.autogeneral.codeTest.model.rest.ToDoItemValidationError;
import com.autogeneral.codetest.common.StringValidator;
import com.autogeneral.codetest.exception.ToDoItemValidationException;
import com.autogeneral.codetest.model.ToDo;
import com.autogeneral.codetest.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ToDoController {
    @Autowired
    private ToDoService todoService;

    @Autowired
    private StringValidator stringValidator;

    @PostMapping(path = "/todo")
    public ToDo createTodo(@RequestBody ToDo todo) {
        ToDoItemValidationError error = stringValidator.validateToDoItemText(todo.getText());
        if (error != null) {
            throw new ToDoItemValidationException(error);
        }

        return todoService.createTodo(todo);
    }

    @GetMapping(path = "/todo/{id}")
    public ToDo getTodo(@PathVariable("id") int id) {
        return todoService.getTodoById(id);
    }

    @PatchMapping(path = "/todo/{id}")
    public ToDo updateTodo(@PathVariable("id") int id, @RequestBody ToDo todo) {
        ToDoItemValidationError error = stringValidator.validateToDoItemText(todo.getText());
        if (error != null) {
            throw new ToDoItemValidationException(error);
        }

        todo.setId(id);
        return todoService.uploadTodo(todo);
    }
}
