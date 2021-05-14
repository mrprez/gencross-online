package com.mrprez.gencross.online.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.Property;
import com.mrprez.gencross.PropertyOwner;
import com.mrprez.gencross.online.model.modification.CharacterModification;

@Service
public class CharacterCompareService {
	
		
	public CharacterModification compare(Personnage oldCharacter, Personnage newCharacter) {
		return compareSubProperties(oldCharacter, newCharacter);
	}
	
	
	private CharacterModification compareSubProperties(PropertyOwner oldPropertyOwner, PropertyOwner newPropertyOwner) {
		CharacterModification characterModification = new CharacterModification();
		for (Property newProperty : newPropertyOwner) {
			Property oldProperty = oldPropertyOwner.getProperty(newProperty.getFullName());
			if (oldProperty == null) {
				characterModification.addAddedProperty(newProperty.getAbsoluteName());
			} else if ( ! areEquals(newProperty, oldPropertyOwner.getProperty(newProperty.getFullName()))) {
				characterModification.addModifiedProperty(newProperty.getAbsoluteName());
			} else if (newProperty.getSubProperties() != null && oldProperty.getSubProperties() != null) {
				characterModification.merge(compareSubProperties(newProperty, oldProperty));
			}
		}
		
		for (Property oldProperty : oldPropertyOwner) {
			if (newPropertyOwner.getProperty(oldProperty.getFullName()) == null) {
				characterModification.addDeletedProperty(oldProperty.getFullName());
			}
		}
		
		return characterModification;
	}
	
	private boolean areEquals(Property property1, Property property2) {
		if (property1.isEditable() != property2.isEditable()) {
			return false;
		} else if (property1.isRemovable() != property2.isRemovable()) {
			return false;
		} else if (! Objects.equals(property1.getComment(), property2.getComment())) {
			return false;
		} else if (! Objects.equals(property1.getMax(), property2.getMax())) {
			return false;
		} else if (! Objects.equals(property1.getMin(), property2.getMin())) {
			return false;
		} else if (! Objects.equals(property1.getOptions(), property2.getOptions())) {
			return false;
		} else if (! Objects.equals(property1.getSpecification(), property2.getSpecification())) {
			return false;
		} else if (! Objects.equals(property1.getValue(), property2.getValue())) {
			return false;
		}
		if (property1.getSubProperties() == null && property2.getSubProperties() == null) {
			return true;
		} else if (property1.getSubProperties() == null && property2.getSubProperties() != null) {
			return false;
		} else if (property1.getSubProperties() != null && property2.getSubProperties() == null) {
			return false;
		} else if (! Objects.equals(property1.getSubProperties().canRemoveElement(), property2.getSubProperties().canRemoveElement())) {
			return false;
		} else if (! Objects.equals(property1.getSubProperties().isFixe(), property2.getSubProperties().isFixe())) {
			return false;
		} else if (! Objects.equals(property1.getSubProperties().isOpen(), property2.getSubProperties().isOpen())) {
			return false;
		} else if (! Objects.equals(property1.getSubProperties().getOptions().keySet(), property2.getSubProperties().getOptions().keySet())) {
			return false;
		}
		return true;
	}
	
	
	
}
