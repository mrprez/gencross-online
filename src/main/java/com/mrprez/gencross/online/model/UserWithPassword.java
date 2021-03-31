package com.mrprez.gencross.online.model;

public class UserWithPassword {
	private User user;
	private String hash;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
}
