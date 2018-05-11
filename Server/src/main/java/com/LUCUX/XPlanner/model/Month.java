package com.LUCUX.XPlanner.model;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Month extends TodosBaseDate {
	
	@JsonIgnore
	public List<Week> weeks = new ArrayList<Week>();
	@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE})
	public List<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<Week> weeks) {
		this.weeks = weeks;
	}
}
