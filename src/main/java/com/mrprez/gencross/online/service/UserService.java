package com.mrprez.gencross.online.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.online.dao.UserDao;
import com.mrprez.gencross.online.model.User;
import com.mrprez.gencross.online.model.id.UserId;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public void createUser(String username, String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		String passwordHash = passwordEncoder.encode(password);
		userDao.createUser(user, passwordHash);
	}


	public void updateUser(UserId userId, String username, String email, String password) {
		User user = userDao.get(userId);
		if (StringUtils.isNotBlank(username)) {
			user.setUsername(username);
		}
		if (StringUtils.isNotBlank(email)) {
			user.setEmail(email);
		}
		userDao.updateUser(user);
		if (StringUtils.isNotBlank(password)) {
			String passwordHash = passwordEncoder.encode(password);
			userDao.updatePassword(userId, passwordHash);
		}
	}

}
