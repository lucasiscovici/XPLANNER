package com.LUCUX.XPlanner.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ElementCollection;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFilter;


@Entity
@JsonFilter("JoinedFilter")
public class UserInfo extends IDS implements CrudD {
		
	
	public static String[] getFiltered = {"sess","getSess"};

	public User user;
	
	public List<Session> sess = new ArrayList<Session>();
	
	public Session sessCur = null;
	
	
	
	@JsonIgnore
	@OneToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval=true)
	public List<Session> getSess() {
		return sess;
	}
	
	public void setSess(List<Session> sess) {
		this.sess = sess;
	}
	
	@OneToOne(optional=true)
	public Session getSessCur() {
		return sessCur;
	}
	
	public void setSessCur(Session sessCur) {
		this.sessCur = sessCur;
	}

}
