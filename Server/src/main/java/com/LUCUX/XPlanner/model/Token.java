package com.LUCUX.XPlanner.model;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Token")
public class Token extends IDS {

	
	public String token;
	public Boolean valable;
	
	@Column(length=10000)
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getValable() {
		return valable;
	}
	public void setValable(Boolean valable) {
		this.valable = valable;
	}
	
}
