package com.mrprez.gencross.online.model;

import java.time.LocalDateTime;

import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public class Table {
	private TableId id;
	private String name;
	private String game;
	private LocalDateTime creationDate;
	private UserId gmId;
	
	
	public TableId getId() {
		return id;
	}
	public void setId(TableId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public UserId getGmId() {
		return gmId;
	}
	public void setGmId(UserId gmId) {
		this.gmId = gmId;
	}
	

}
