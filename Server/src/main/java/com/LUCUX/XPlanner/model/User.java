package com.LUCUX.XPlanner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import com.LUCUX.XPlanner.controller.EMCrud;
import com.LUCUX.XPlanner.repository.UserInfoRepository;
import com.LUCUX.XPlanner.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@JsonFilter("USERFilter")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String[] getFiltered = {"tokens","password"};
	
	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(cascade= {CascadeType.PERSIST,CascadeType.REMOVE})
	public  List<Token> tokens = new ArrayList<Token>();
	
	@OneToOne(cascade=CascadeType.PERSIST)
	public UserInfo userInfo;

	@Column(name = "first_name", length = 75)
	private String firstName;

	@Column(name = "last_name", length = 80)
	private String lastName;

	@Column(name = "username", length = 65, unique = true)
	private String username;

	//@JsonIgnore
	@Column(name = "password", length = 64)
	public String password;
	
	public String password2;

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@Column(name = "email", unique = true, length = 115)
	private String email;

	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
