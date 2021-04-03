package com.mrprez.gencross.online.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.online.dao.UserDao;
import com.mrprez.gencross.online.model.User;
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
		
		return new GencrossUserDetail(userWithPassword.getUser(), userWithPassword.getHash());
	}
	

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		if (! passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
	}
	
	public User getAuthenticatedUser() {
		return ((GencrossUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
	}
	
	public class GencrossUserDetail implements UserDetails {
		private User user;
		private String passwordHash;
		private List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		
		
		public GencrossUserDetail(User user, String passwordHash) {
			this.user = user;
			this.passwordHash = passwordHash;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		@Override
		public String getPassword() {
			return passwordHash;
		}

		@Override
		public String getUsername() {
			return user.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
		public User getUser() {
			return user;
		}
	}

}
