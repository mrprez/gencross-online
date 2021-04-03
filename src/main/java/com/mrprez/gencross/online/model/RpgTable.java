package com.mrprez.gencross.online.model;

import java.time.LocalDateTime;

import com.mrprez.gencross.online.model.id.RpgTableId;
import com.mrprez.gencross.online.model.id.UserId;

public class RpgTable {
	private RpgTableId id;
	private String name;
	private String type;
	private LocalDateTime creationDate;
	private UserId gmId;
	
	
	public RpgTableId getId() {
		return id;
	}
	public void setId(RpgTableId id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
