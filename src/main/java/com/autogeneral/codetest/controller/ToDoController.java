package com.autogeneral.codetest.controller;

import com.autogeneral.codetest.common.StringValidator;
import com.autogeneral.codetest.model.ToDo;
import com.autogeneral.codetest.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ToDoController{
    @Autowired
    private ToDoService todoService;
    @Autowired
    StringValidator stringValidator;

    @PostMapping(path = "/todo")
    public Object createTodo(@RequestBody ToDo todo) {
        stringValidator.validateToDoItemText(todo.getText());

        return todoService.createTodo(todo);
    }

    @GetMapping(path = "/todo/{id}")
    public Object getTodo(@PathVariable("id") int id) {
        ToDo todo = todoService.getTodoById(id);
        return todo == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : todo;
    }

    @PatchMapping(path = "/todo/{id}")
    public Object updateTodo(@PathVariable("id") int id, @RequestBody ToDo todo) {
        stringValidator.validateToDoItemText(todo.getText());

        todo.setId(id);
        return todoService.uploadTodo(todo);
    }
}
