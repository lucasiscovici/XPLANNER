package com.LUCUX.XPlanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFilter;

@Entity
@JsonFilter("SessionFilter")
public class Session extends TodosBaseDate {

	public static String[] getFiltered = {"goals","tasks","months","ui","todos"};
	
	public UserInfo ui;
		
	public List<Month> months = new ArrayList<Month>();
	
	//__FUTURE__
	//List<Goal> goals = new ArrayList<Goal>();
	//List<Task> tasks = new ArrayList<Task>();
	
	// 	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	// public List<Goal> getGoals() {
	// 	return goals;
	// }
	// public void setGoals(List<Goal> goals) {
	// 	this.goals = goals;
	// }
	
	// @OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	// public List<Task> getTasks() {
	// 	return tasks;
	// }
	// public void setTasks(List<Task> tasks) {
	// 	this.tasks = tasks;
	// };
	
	
	//GETTER_SETTER
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	public UserInfo getUi() {
		return ui;
	}
	
	public void setUi(UserInfo ui) {
		this.ui = ui;
	}
	
	
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	public List<Month> getMonths() {
		return months;
	}
	public void setMonths(List<Month> months) {
		this.months = months;
	}

	public String toString(){
		return "SESSION "+this.getId()+"\n";
	}
	
}
