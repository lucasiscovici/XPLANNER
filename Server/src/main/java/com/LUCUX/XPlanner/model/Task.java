package com.LUCUX.XPlanner.model;



import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
public class Task extends IDS {
	

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
