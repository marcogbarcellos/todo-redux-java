package com.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dao.UserDAO;
import com.todo.dto.SearchDTO;
import com.todo.model.User;

@Service
public class UserService implements ICrudService<User> {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User getById(long userId) {
		User obj = userDAO.getById(userId);
		return obj;
	}
	
	@Override
	public List<User> getAll(SearchDTO dto){
		return userDAO.getAllWithFilter(dto);
	}
	
	@Override
	public synchronized User add(User user){
       if ( user.getName() == null || user.getRole() == null || 
    		   userDAO.userExists(user.getName(), user.getRole())) {
    	   return null;
       } else {
         return userDAO.add(user);
       }
	}
	
	@Override
	public User update(User user) {
		return userDAO.update(user);
	}
	
	@Override
	public boolean delete(long userId) {
		return userDAO.delete(userId);
	}
}
