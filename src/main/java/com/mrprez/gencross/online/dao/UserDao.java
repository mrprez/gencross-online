package com.mrprez.gencross.online.dao;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.User;

public interface UserDao {

	void createUser(@Param("user") User user, @Param("passwordHash") byte[] passwordHash);
	
}
