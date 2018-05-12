package com.LUCUX.XPlanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class BaseDate extends Base {
	 public Date date;
	 //__FUTURE__
	//  @JsonIgnore
	// private List<Event> events = new ArrayList<Event>();
	
	// @OneToMany(cascade=CascadeType.ALL)
	// @ElementCollection
	// public List<Event> getEvents() {
	// 	return events;
	// }

	// public void setEvents(List<Event> events) {
	// 	this.events = events;
	// }

	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
