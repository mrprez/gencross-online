package com.mrprez.gencross.online.web.dto;

import java.util.List;

public class CharacterDto {
	private List<PropertyDto> properties;
	private List<PointPoolDto> pointPools;
	private List<String> errors;
	

	public List<PropertyDto> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyDto> properties) {
		this.properties = properties;
	}

	public List<PointPoolDto> getPointPools() {
		return pointPools;
	}

	public void setPointPools(List<PointPoolDto> pointPools) {
		this.pointPools = pointPools;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
	
}
