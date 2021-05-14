package com.mrprez.gencross.online.model.modification;

import java.util.HashSet;
import java.util.Set;

public class CharacterModification {
	private Set<String> modifiedProperties = new HashSet<String>();
	private Set<String> deletedProperties = new HashSet<String>();
	private Set<String> addedProperties = new HashSet<String>();
	

	public Set<String> getModifiedProperties() {
		return modifiedProperties;
	}
	
	public Set<String> getDeletedProperties() {
		return deletedProperties;
	}

	public Set<String> getAddedProperties() {
		return addedProperties;
	}


	public void addModifiedProperty(String modifiedProperty) {
		this.modifiedProperties.add(modifiedProperty);
	}

	public void addDeletedProperty(String deletedProperty) {
		this.deletedProperties.add(deletedProperty);
	}

	public void addAddedProperty(String addedProperty) {
		this.addedProperties.add(addedProperty);
	}
	
	public void merge(CharacterModification otherModification) {
		this.modifiedProperties.addAll(otherModification.modifiedProperties);
		this.deletedProperties.addAll(otherModification.deletedProperties);
		this.addedProperties.addAll(otherModification.addedProperties);
	}
	
	public int size() {
		return modifiedProperties.size() + deletedProperties.size() + addedProperties.size();
	}

}
