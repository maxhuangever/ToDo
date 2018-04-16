package com.autogeneral.codetest.service;

import com.autogeneral.codetest.dao.ToDoRepository;
import com.autogeneral.codetest.exception.ToDoItemNotFoundException;
import com.autogeneral.codetest.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;

    public ToDo createTodo(ToDo todo) {
        todo.setIsCompleted(false);
        todo.setCreatedAt(new Date());
        return toDoRepository.save(todo);
    }

    public ToDo getTodoById(long id) {
        ToDo entity = toDoRepository.findOne(id);
        if (entity == null) {
            throw new ToDoItemNotFoundException("Item with " + id + " not found.");
        }

        return entity;
    }

    public ToDo uploadTodo(ToDo todo) {
        ToDo entity = toDoRepository.findOne(todo.getId());
        if (entity == null) {
            throw new ToDoItemNotFoundException("Item with " + todo.getId() + " not found.");
        }

        if (todo.getText() != null) {
            entity.setText(todo.getText());
        }
        if (todo.getIsCompleted() != null) {
            entity.setIsCompleted(todo.getIsCompleted());
        }
        return toDoRepository.save(entity);
    }
}
