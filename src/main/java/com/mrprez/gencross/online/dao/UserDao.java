package com.mrprez.gencross.online.dao;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.User;
import com.mrprez.gencross.online.model.UserWithPassword;
import com.mrprez.gencross.online.model.id.UserId;

public interface UserDao {
	
	User get(@Param("userId") UserId userId);

	UserWithPassword getWithPassword(@Param("username") String username);

	void createUser(@Param("user") User user, @Param("passwordHash") String passwordHash);

	void updateUser(User user);

	void updatePassword(@Param("userId") UserId userId, @Param("passwordHash") String passwordHash);

	User getFromUsername(@Param("username") String username);
	
	User getFromEmail(@Param("email") String email);
	
}
