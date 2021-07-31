package com.mrprez.gencross.online.exception;

@SuppressWarnings("serial")
public class UsernameAlreadyExistException extends Exception {
	private final String username;

	public UsernameAlreadyExistException(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

}
