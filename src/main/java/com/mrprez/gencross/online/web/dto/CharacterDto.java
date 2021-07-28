package com.mrprez.gencross.online.web.dto;

import java.util.List;

public class CharacterDto {
	private String phase;
	private List<PropertyDto> properties;
	private List<PointPoolDto> pointPools;
	private List<String> errors;
	private boolean nextPhase;
	private boolean nextPhaseAvailable;

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

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
	
	public boolean isNextPhase() {
		return nextPhase;
	}

	public void setNextPhase(boolean nextPhase) {
		this.nextPhase = nextPhase;
	}

	public boolean isNextPhaseAvailable() {
		return nextPhaseAvailable;
	}

	public void setNextPhaseAvailable(boolean nextPhaseAvailable) {
		this.nextPhaseAvailable = nextPhaseAvailable;
	}
	
	
	
	
	
}
