package com.LUCUX.XPlanner.response;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

public class UserTransfer {

	private String username;
	private List<String> roles;
	private String token;
	private HttpStatus status;

	public UserTransfer(String username, List<String> roles, String token, HttpStatus status) {
		this.roles = roles;
		this.token = token;
		this.username = username;
		this.status = status;
	}

	public UserTransfer() {
		this.token = "";
		this.username = "";
		this.roles = Collections.emptyList();
		this.status = HttpStatus.NOT_FOUND;
	}

	public List<String> getRoles() {
		return this.roles;
	}

	public String getToken() {
		return this.token;
	}

	public String getUsername() {
		return this.username;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	

}
