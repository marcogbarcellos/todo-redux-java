package com.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name = "todos")
public class Todo extends AbstractTimeStampModel {
  
  private static final long serialVersionUID = 1L;
  
  @Column(name = "text", nullable = false)
  private String text;
    
  @Column(name = "completed", nullable = false)
  private Boolean completed;
  
  @Column(name = "priority", nullable = false)
  private Integer priority;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }
  
    public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", text=" + text +
                ", completed=" + completed +
                '}';
    }

    @Override
    public void onPrePersist() {
      if ( this.completed == null ) {
        this.completed = false;
      }
      super.onPrePersist();
    }
    
    
}