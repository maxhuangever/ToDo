package com.autogeneral.codetest.dao;

import com.autogeneral.codetest.model.ToDo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ToDoRepository extends PagingAndSortingRepository<ToDo, Integer> {
}
