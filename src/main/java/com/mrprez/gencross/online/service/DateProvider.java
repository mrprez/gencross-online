package com.mrprez.gencross.online.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class DateProvider {
	
	public LocalDateTime now() {
		return LocalDateTime.now();
	}

}
