package com.LUCUX.XPlanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Base extends IDS{

	
	String name;
	Date date;
	@JsonIgnore
	List<ListeItem> learn = new ArrayList<ListeItem>();
	@JsonIgnore
	List<ListeItem> will = new ArrayList<ListeItem>();

	@Column(length=10000)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@OneToMany(targetEntity=ListeItem.class)
	@ElementCollection
	public List<ListeItem> getLearn() {
		return learn;
	}
	public void setLearn(List<ListeItem> learn) {
		this.learn = learn;
	}
	@OneToMany(targetEntity=ListeItem.class)
	@ElementCollection
	public List<ListeItem> getWill() {
		return will;
	}
	public void setWill(List<ListeItem> will) {
		this.will = will;
	}
	
	
	
}