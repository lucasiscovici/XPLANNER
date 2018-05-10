package com.LUCUX.XPlanner.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public class TodosC extends IDS {
	
	List<Todo> todosL= new ArrayList<Todo>();
	
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true,fetch = FetchType.EAGER)
	@ElementCollection
	public List<Todo> getTodos() {
		return todosL;
	}

	public void setTodos(List<Todo> todos) {
		this.todosL = todos;
	}
}