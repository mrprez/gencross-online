package com.mrprez.gencross.online.dbbuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public class TableTestDbBuilder {
	private final String name;
	private String game;
	private LocalDateTime creationDate;
	private UserId gmId;
	
	
	public static TableTestDbBuilder newTable(String name) {
		return new TableTestDbBuilder(name);
	}
	
	private TableTestDbBuilder(String name) {
		super();
		this.name = name;
		this.game = "TestGame";
		this.creationDate = LocalDateTime.now();
	}
	
	
	public TableTestDbBuilder withGame(String game) {
		this.game = game;
		return this;
	}
	
	public TableTestDbBuilder withGm(UserId gmId) {
		this.gmId = gmId;
		return this;
	}
	
	public TableTestDbBuilder withCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
		return this;
	}
	
	public TableTestDbBuilder withCreationDate(String creationDate) {
		this.creationDate = LocalDateTime.parse(creationDate);
		return this;
	}
	
	public TableId save(Connection connection) throws SQLException {
		if (gmId == null) {
			gmId = UserTestDbBuilder.newUser("GM_of_"+name).save(connection);
		}
		
		String insertRequest = "INSERT INTO rpg_table (name, game, gm_id, creation_date) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, game);
		preparedStatement.setInt(3, gmId.getInt());
		preparedStatement.setTimestamp(4, Timestamp.valueOf(creationDate));
		preparedStatement.execute();
		ResultSet generatedKeyResultSet = preparedStatement.getGeneratedKeys();
		generatedKeyResultSet.first();
		return new TableId(generatedKeyResultSet.getInt(1));
	}
	

}
