package com.mrprez.gencross.online.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {

	private final String username;

	public UserNotFoundException(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
