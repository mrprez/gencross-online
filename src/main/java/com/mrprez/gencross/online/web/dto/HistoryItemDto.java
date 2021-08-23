package com.mrprez.gencross.online.web.dto;

import java.util.Date;

public class HistoryItemDto {
	private Date date;
	private String oldValue;
	private String newValue;
	private String absoluteName;
	private String pointPool;
	private int cost;
	private int action;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getAbsoluteName() {
		return absoluteName;
	}
	public void setAbsoluteName(String absoluteName) {
		this.absoluteName = absoluteName;
	}
	public String getPointPool() {
		return pointPool;
	}
	public void setPointPool(String pointPool) {
		this.pointPool = pointPool;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	
}
