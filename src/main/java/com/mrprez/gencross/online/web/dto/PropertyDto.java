package com.mrprez.gencross.online.web.dto;

import java.util.List;

public class PropertyDto {
	private String name;
	private String absoluteName;
	private String value;
	private boolean editable;
	private List<PropertyDto> subProperties;
	private boolean subPropertiesListFixe;
	private boolean subPropertiesListOpen;
	private List<String> subPropertiesListOptions;
	
	
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
	public boolean isSubPropertiesListFixe() {
		return subPropertiesListFixe;
	}
	public void setSubPropertiesListFixe(boolean subPropertiesListFixe) {
		this.subPropertiesListFixe = subPropertiesListFixe;
	}
	public boolean isSubPropertiesListOpen() {
		return subPropertiesListOpen;
	}
	public void setSubPropertiesListOpen(boolean subPropertiesListOpen) {
		this.subPropertiesListOpen = subPropertiesListOpen;
	}
	public List<String> getSubPropertiesListOptions() {
		return subPropertiesListOptions;
	}
	public void setSubPropertiesListOptions(List<String> subPropertiesListOptions) {
		this.subPropertiesListOptions = subPropertiesListOptions;
	}
	
	
	
}
