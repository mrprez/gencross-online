package com.mrprez.gencross.online.model;

import com.mrprez.gencross.online.model.id.UserId;

public class User {
	private UserId id;
	private String username;
	private String email;
	
	
	public UserId getId() {
		return id;
	}
	public void setId(UserId id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
