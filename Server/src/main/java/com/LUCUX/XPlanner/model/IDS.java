package com.LUCUX.XPlanner.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class IDS{
	long id; 
	String fId = null;
	
	public String getfId() {
		return fId;
	}
	public void setfId(String fId) {
		this.fId = fId;
	}
	public IDS() {
		// TODO Auto-generated constructor stub
	}
	@GeneratedValue
	@Id
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}