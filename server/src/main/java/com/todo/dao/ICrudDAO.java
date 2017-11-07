package com.todo.dao;

import java.util.List;

import com.todo.dto.SearchDTO;
import com.todo.model.IModel;

public interface ICrudDAO<T extends IModel> {
	
    T getById(long id);
    T add(T obj);
    T update(T obj);
    boolean delete(long id);
    List<T> getAll();
    List<T> getAllWithFilter(SearchDTO dto);
}
