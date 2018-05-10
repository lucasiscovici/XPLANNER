package com.LUCUX.XPlanner.model;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
@Entity
public class Goal  extends IDS{

	String title;
	String why;
	List<ListeItem> how = new ArrayList<ListeItem>();;

	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	public List<ListeItem> getHow() {
		return how;
	}
	public void setHow(List<ListeItem> how) {
		this.how = how;
	}
	

	
}
