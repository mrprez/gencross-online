package com.mrprez.gencross.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.online.dao.UserDao;
import com.mrprez.gencross.online.model.UserWithPassword;

@Service
public class GencrossAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if ( ! (authentication.getCredentials() instanceof String) ) {
			return null;
		}
		
		UserWithPassword userWithPassword = userDao.getWithPassword(username);
		if (userWithPassword == null) {
			throw new UsernameNotFoundException(username+" not found");
		}
		
		return User.builder().username(username).password(userWithPassword.getHash()).roles("USER").build();
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (! passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
	}

}
