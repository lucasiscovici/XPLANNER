package com.LUCUX.XPlanner.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;


@Entity
public class Todos extends IDS {
	List<Todo> todosL= new ArrayList<Todo>();
	
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true,fetch = FetchType.EAGER)
	@ElementCollection
	@JoinTable(
	        name = "todos_todo",
	        joinColumns = @JoinColumn(name = "todos_id"),
	        inverseJoinColumns = @JoinColumn(name = "todo_id")
	    )
	public List<Todo> getTodos() {
		return todosL;
	}

	public void setTodos(List<Todo> todos) {
		this.todosL = todos;
	}
}
