package com.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name = "users")
public class User extends AbstractTimeStampModel {
	
	private static final long serialVersionUID = 1L;
  
  @Column(name = "name", nullable = false)
  private String name;
    
  @Column(name = "role", nullable = false)
  private String role;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name=" + name +
                ", role=" + role +
                '}';
    }
    
}