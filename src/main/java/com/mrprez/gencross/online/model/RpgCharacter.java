package com.mrprez.gencross.online.model;

import java.time.LocalDateTime;

import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public class RpgCharacter {
	private CharacterId id;
	private String name;
	private TableId tableId;
	private UserId playerId;
	private byte[] data;
	private LocalDateTime creationDate;
	
	
	public CharacterId getId() {
		return id;
	}
	public void setId(CharacterId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TableId getTableId() {
		return tableId;
	}
	public void setTableId(TableId tableId) {
		this.tableId = tableId;
	}
	public UserId getPlayerId() {
		return playerId;
	}
	public void setPlayerId(UserId playerId) {
		this.playerId = playerId;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
}
