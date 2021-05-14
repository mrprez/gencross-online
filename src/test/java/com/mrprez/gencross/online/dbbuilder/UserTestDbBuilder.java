package com.mrprez.gencross.online.dbbuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mrprez.gencross.online.model.id.UserId;

public class UserTestDbBuilder {
	private final String username;
	private String email;
	private String passwordHash;
	
	
	public static UserTestDbBuilder newUser(String username) {
		return new UserTestDbBuilder(username);
	}
	
	private UserTestDbBuilder(String username) {
		super();
		this.username = username;
		this.email = username.replaceAll("[^a-zA-Z0-9]", "")+"@test.com";
		this.passwordHash = "passwordHash";
	}
	
	
	public UserTestDbBuilder withEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserTestDbBuilder withPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}
	
	public UserId save(Connection connection) throws SQLException {
		String insertRequest = "INSERT INTO user (username, email, password_hash) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, email);
		preparedStatement.setString(3, passwordHash);
		preparedStatement.execute();
		ResultSet generatedKeyResultSet = preparedStatement.getGeneratedKeys();
		generatedKeyResultSet.first();
		return new UserId(generatedKeyResultSet.getInt(1));
	}
	

}
