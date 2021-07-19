package com.mrprez.gencross.online.web.dto;

import java.util.List;

public class PropertyDto {
	private String name;
	private String absoluteName;
	private String value;
	private String valueType;
	private String minValue;
	private String maxValue;
	private String valueOffset;
	private List<String> options;
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
	public String getMinValue() {
		return minValue;
	}
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	public String getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	public String getValueOffset() {
		return valueOffset;
	}
	public void setValueOffset(String valueOffset) {
		this.valueOffset = valueOffset;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
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
