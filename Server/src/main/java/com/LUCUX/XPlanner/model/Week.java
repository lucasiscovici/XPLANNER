package com.LUCUX.XPlanner.model;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Week extends TodosBaseDate {
	public List<Day> days = new ArrayList<Day>();



	@JsonIgnore
	@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE})
	public List<Day> getDays() {
		return days;
	}

	public void setDays(List<Day> days) {
		this.days = days;
	}
}
