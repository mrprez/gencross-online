package com.mrprez.gencross.online.dbbuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.disk.PersonnageFactory;
import com.mrprez.gencross.disk.PersonnageSaver;
import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public class CharacterTestDbBuilder {
	private final String name;
	private final TableId tableId;
	private UserId playerId;
	private byte[] data;
	private LocalDateTime creationDate;
	
	
	public static CharacterTestDbBuilder newCharacter(String name, TableId tableId) throws Exception {
		return new CharacterTestDbBuilder(name, tableId);
	}
	
	private CharacterTestDbBuilder(String name, TableId tableId) throws Exception {
		super();
		this.name = name;
		this.tableId = tableId;
		Personnage personnage = new PersonnageFactory().buildNewPersonnage("Test");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PersonnageSaver.savePersonnage(personnage, baos);
		this.data = baos.toByteArray();
		this.creationDate = LocalDateTime.now();
	}
	
	public CharacterTestDbBuilder withPlayer(UserId playerId) {
		this.playerId = playerId;
		return this;
	}
	
	public CharacterTestDbBuilder withCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
		return this;
	}
	
	public CharacterTestDbBuilder withData(byte[] data) {
		this.data = data;
		return this;
	}
	
	
	public CharacterId save(Connection connection) throws SQLException {
		String insertRequest = "INSERT INTO rpg_character (name, table_id, player_id, data, creation_date) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(insertRequest, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, tableId.getInt());
		if (playerId != null) {
			preparedStatement.setInt(3, playerId.getInt());
		} else {
			preparedStatement.setNull(3, Types.INTEGER);
		}
		preparedStatement.setBlob(4, new ByteArrayInputStream(data));
		preparedStatement.setTimestamp(5, Timestamp.valueOf(creationDate));
		preparedStatement.execute();
		ResultSet generatedKeyResultSet = preparedStatement.getGeneratedKeys();
		generatedKeyResultSet.first();
		return new CharacterId(generatedKeyResultSet.getInt(1));
	}
	

}
