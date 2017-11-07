package com.todo.dto;

public class TodoDTO implements SearchDTO {

  private Long[] id;
  
  private String[] texts;
  
  private Integer[] priorities;
      
  private Boolean completed;
  
  public Long[] getId() {
    return id;
  }
  
  public void setId(Long[] id) {
    this.id = id;
  }
  
  public String[] getTexts() {
    return texts;
  }
  
  public void setTexts(String[] texts) {
    this.texts = texts;
  }
  
  public Boolean getCompleted() {
    return completed;
  }
  
  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }
  
  public Integer[] getPriorities() {
    return priorities;
  }
  
  public void setId(Integer[] priorities) {
    this.priorities = priorities;
  }
  
  
  @Override
  public String toString() {
      return "Todo DTO{" +
              "id=" + id +
              ", texts=" + texts +
              ", completed=" + completed +
              '}';
  }
  
}