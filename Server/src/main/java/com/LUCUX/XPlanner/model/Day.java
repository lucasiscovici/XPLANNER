package com.LUCUX.XPlanner.model;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@Entity
public class Day extends TodosBaseDate {
	
	List<ListeItem> toDoList = new ArrayList<ListeItem>();
	List<ListeItem> toDoListAll = new ArrayList<ListeItem>();
	
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	public List<ListeItem> getToDoList() {
		return toDoList;
	}
	public void setToDoList(List<ListeItem> toDoList) {
		this.toDoList = toDoList;
	}
	
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	public List<ListeItem> getToDoListAll() {
		return toDoListAll;
	}
	public void setToDoListAll(List<ListeItem> toDoListAll) {
		this.toDoListAll = toDoListAll;
	};
	

}
