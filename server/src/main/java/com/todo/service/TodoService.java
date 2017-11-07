package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dao.TodoDAO;
import com.todo.dto.SearchDTO;
import com.todo.dto.TodoPriorityCountDTO;
import com.todo.model.Todo;

@Service
public class TodoService implements ICrudService<Todo> {
  
  @Autowired
  private TodoDAO todoDAO;
  
  @Override
  public Todo getById(long todoId) {
    Todo obj = todoDAO.getById(todoId);
    return obj;
  }
  
  @Override
  public List<Todo> getAll(SearchDTO dto){
    return todoDAO.getAllWithFilter(dto);
  }
  
  @Override
  public synchronized Todo add(Todo todo){
    if ( todo.getText() == null || todoDAO.todoExists(todo.getText()) ) {
      return null;
    }
    return todoDAO.add(todo);
  }
  
  @Override
  public Todo update(Todo todo) {
    return todoDAO.update(todo);
  }
  
  @Override
  public boolean delete(long todoId) {
    return todoDAO.delete(todoId);
  }
  
  public List<TodoPriorityCountDTO> getPrioritiesCounts(Integer[] priorities ) {
    return  todoDAO.getPrioritiesCount(priorities);
  }
}
