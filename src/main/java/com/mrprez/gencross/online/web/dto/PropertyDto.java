package com.mrprez.gencross.online.web.dto;

import java.util.List;

public class PropertyDto {
	private String name;
	private String absoluteName;
	private String value;
	private boolean editable;
	private List<PropertyDto> subProperties;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbsoluteName() {
		return absoluteName;
	}
	public void setAbsoluteName(String absoluteName) {
		this.absoluteName = absoluteName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public List<PropertyDto> getSubProperties() {
		return subProperties;
	}
	public void setSubProperties(List<PropertyDto> subProperties) {
		this.subProperties = subProperties;
	}
}
