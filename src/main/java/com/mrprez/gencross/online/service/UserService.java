package com.mrprez.gencross.online.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	
	public void createUser(String username, String email, String password) {
		System.out.println("username="+username);
		System.out.println("email="+email);
		System.out.println("password="+password);
	}

}
