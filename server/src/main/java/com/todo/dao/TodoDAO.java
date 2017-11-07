package com.todo.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.todo.dto.SearchDTO;
import com.todo.dto.TodoDTO;
import com.todo.dto.TodoPriorityCountDTO;
import com.todo.model.Todo;

@Transactional
@Repository
public class TodoDAO extends AbstractDAO<Todo>{
  
  @PersistenceContext 
  private EntityManager entityManager;  
  
  @Override
  public Todo getById(long todoId) {
    Todo todo = entityManager.find(Todo.class, todoId); 
    return ( todo != null && !todo.getDeleted() ) ? todo : null;
  }

  @Override
  public Todo update(Todo todo) {
    Todo todoToUpdate = getById(todo.getId());
    if ( todoToUpdate != null && todo.getText() != null ) {
      todoToUpdate.setText(todo.getText());
    }
    if ( todoToUpdate != null && todo.getCompleted() != null ) {
      todoToUpdate.setCompleted(todo.getCompleted());
    }
    if ( todoToUpdate != null && todo.getPriority() != null ) {
      todoToUpdate.setPriority(todo.getPriority());
    }
    return (Todo) entityManager.merge(todoToUpdate);
  }

  @Override
  public boolean delete(long todoId) {
    Todo todoToRemove = getById(todoId);
    if ( todoToRemove != null ) {
      todoToRemove.setDeleted(true);
      entityManager.merge(todoToRemove);
      return true;
    }
    return false;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Todo> getAllWithFilter(SearchDTO dto) {
    TodoDTO todoDto = (TodoDTO) dto;
    
    StringBuilder hql = new StringBuilder(" FROM Todo as u WHERE u.deleted=false ");
    
    //validating and adding parameters(if they exist)
    if ( todoDto.getId() != null && todoDto.getId().length > 0 ) {
      hql.append(" AND u.id in (:ids) ");
    }
    if ( todoDto.getTexts() != null && todoDto.getTexts().length > 0 ) {
      hql.append("AND u.text in (:texts) ");
    }
    if ( todoDto.getCompleted() != null ) {
      hql.append(" AND u.completed = :completed ");
    }
    hql.append(" ORDER BY u.priority ASC");
    
    Query query = entityManager.createQuery(hql.toString());
    //setting query arguments after having it defined
    if ( todoDto.getId() != null && todoDto.getId().length > 0 ) {
      query.setParameter("ids", Arrays.asList(todoDto.getId()));
    }
    if ( todoDto.getTexts() != null && todoDto.getTexts().length > 0 ) {
      query.setParameter("texts", Arrays.asList(todoDto.getTexts()));
    }
    if ( todoDto.getCompleted() != null ) {
      query.setParameter("completed", todoDto.getCompleted() );
    }
    if ( todoDto.getPriorities() != null ) {
      query.setParameter("priorities", Arrays.asList(todoDto.getPriorities()));
    }
    
    return (List<Todo>) query.getResultList();
  }
  
  @SuppressWarnings("unchecked")
  public List<TodoPriorityCountDTO> getPrioritiesCount(Integer[] priorities) {
    
    StringBuilder hql = new StringBuilder(" select u.priority, count(*) ");
    hql.append(" FROM Todo as u ");
    
    if(priorities == null || priorities.length < 1 ) {
      hql.append(" WHERE 1=1 ");
    } else {
      hql.append(" WHERE u.priority in (:priorities) ");
    }
    
    hql.append(" GROUP BY u.priority ");
    hql.append(" ORDER BY u.priority DESC ");
    
    Query query = entityManager.createQuery(hql.toString());
    //setting query arguments after having it defined
    if(priorities != null && priorities.length > 0 ) {
      query.setParameter("priorities", Arrays.asList(priorities));
    }
    
    return (List<TodoPriorityCountDTO>) query.getResultList();
  }

  public boolean todoExists(String text) {
    String hql = "FROM Todo as u WHERE u.text = ?";
    int count = entityManager.createQuery(hql).setParameter(1, text)
                  .getResultList().size();
    return count > 0 ? true : false;
  }

}
