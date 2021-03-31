package com.mrprez.gencross.online.dao;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.User;
import com.mrprez.gencross.online.model.UserWithPassword;

public interface UserDao {

	void createUser(@Param("user") User user, @Param("passwordHash") String passwordHash);
	
	UserWithPassword getWithPassword(@Param("username") String username);
	
}
