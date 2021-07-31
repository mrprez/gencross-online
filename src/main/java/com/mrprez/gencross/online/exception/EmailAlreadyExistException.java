package com.mrprez.gencross.online.exception;

@SuppressWarnings("serial")
public class EmailAlreadyExistException extends Exception {
	private final String email;

	public EmailAlreadyExistException(String email) {
		super();
		this.email = email;
	}
	
	public String getUsername() {
		return email;
	}

}
