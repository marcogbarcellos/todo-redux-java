package com.todo.service;

import java.util.List;

import com.todo.dto.SearchDTO;

public interface ICrudService<T> {
     List<T> getAll(SearchDTO dto);
     T getById(long id);
     T add(T obj);
     T update(T obj);
     boolean delete(long id);
}
