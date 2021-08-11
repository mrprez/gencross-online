package com.mrprez.gencross.online.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.Property;
import com.mrprez.gencross.disk.PersonnageFactory;
import com.mrprez.gencross.disk.PersonnageSaver;
import com.mrprez.gencross.disk.PluginDescriptor;
import com.mrprez.gencross.online.dao.CharacterDao;
import com.mrprez.gencross.online.dao.TableDao;
import com.mrprez.gencross.online.exception.NotAllowedAccessException;
import com.mrprez.gencross.online.model.LoadedCharacter;
import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.RpgCharacterWithTable;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;
import com.mrprez.gencross.value.Value;

@Service
public class CharacterService {
	
	@Autowired
	private PersonnageFactory personnageFactory;
	
	@Autowired
	private TableDao tableDao;

	@Autowired
	private CharacterDao characterDao;
	
	@Autowired
	private DateProvider dateProvider;
	
	
	public Collection<PluginDescriptor> getPluginList() {
		return personnageFactory.getPluginList();
	}
	
	public void createCharacter(TableId tableId, UserId userId, String name) throws Exception {
		Table table = tableDao.get(tableId);
		if (! table.getGmId().equals(userId)) {
			throw new NotAllowedAccessException();
		}
		Personnage personnage = personnageFactory.buildNewPersonnage(table.getGame());
		RpgCharacter character = new RpgCharacter();
		character.setCreationDate(dateProvider.now());
		character.setName(name);
		character.setTableId(tableId);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PersonnageSaver.savePersonnage(personnage, baos);
		character.setData(baos.toByteArray());
		characterDao.createCharacter(character);
	}

	public LoadedCharacter getCharacter(CharacterId characterId, UserId userId) throws Exception {
		RpgCharacterWithTable rpgCharacterWithTable = characterDao.getRpgCharacterWithTable(characterId);
		if (!rpgCharacterWithTable.getTable().getGmId().equals(userId) 
				&& !rpgCharacterWithTable.getRpgCharacter().getPlayerId().equals(userId)) {
			throw new NotAllowedAccessException();
		}
		RpgCharacter rpgCharacter = rpgCharacterWithTable.getRpgCharacter();
		LoadedCharacter loadedCharacter = new LoadedCharacter();
		loadedCharacter.setId(rpgCharacter.getId());
		loadedCharacter.setName(rpgCharacter.getName());
		loadedCharacter.setPlayerId(rpgCharacter.getPlayerId());
		loadedCharacter.setTableId(rpgCharacter.getTableId());
		loadedCharacter.setData(personnageFactory.loadPersonnage(new ByteArrayInputStream(rpgCharacter.getData())));
		return loadedCharacter;
	}
	
	public RpgCharacterWithTable getRpgCharacterWithTable(CharacterId characterId, UserId userId) {
		RpgCharacterWithTable rpgCharacterWithTable = characterDao.getRpgCharacterWithTable(characterId);
		if (!rpgCharacterWithTable.getTable().getGmId().equals(userId) 
				&& !rpgCharacterWithTable.getRpgCharacter().getPlayerId().equals(userId)) {
			throw new NotAllowedAccessException();
		}
		return rpgCharacterWithTable;
	}

	public Personnage setValue(CharacterId characterId, String propertyName, String valueAsString, UserId userId) throws Exception {
		LoadedCharacter loadedCharacter = getCharacter(characterId, userId);
		Personnage personnage = loadedCharacter.getData();
		Property property = personnage.getProperty(propertyName);
		Value newValue = property.getValue().clone();
		newValue.setValue(valueAsString);
		
		boolean success = personnage.setNewValue(propertyName, newValue);
		if (success) {
			updateCharacterData(characterId, personnage);
		}
		
		return personnage;
	}

	public Personnage addProperty(CharacterId characterId, String parentAbsoluteName, String newPropertyName, String specification, UserId userId) throws Exception {
		LoadedCharacter loadedCharacter = getCharacter(characterId, userId);
		Personnage personnage = loadedCharacter.getData();
		Property parentProperty = personnage.getProperty(parentAbsoluteName);
		Property newProperty;
		if (parentProperty.getSubProperties().getOptions().containsKey(newPropertyName)) {
			newProperty = parentProperty.getSubProperties().getOptions().get(newPropertyName).clone();
			newProperty.setSpecification(specification);
		} else {
			newProperty = parentProperty.getSubProperties().getDefaultProperty().clone();
			newProperty.setName(newPropertyName);
		}
		
		boolean success = personnage.addPropertyToMotherProperty(newProperty);
		if (success) {
			updateCharacterData(characterId, personnage);
		}
		
		return personnage;
	}
	
	public Personnage deleteProperty(CharacterId characterId, String propertyName, UserId userId) throws Exception {
		LoadedCharacter loadedCharacter = getCharacter(characterId, userId);
		Personnage personnage = loadedCharacter.getData();
		Property property = personnage.getProperty(propertyName);
		
		boolean success = personnage.removePropertyFromMotherProperty(property);
		if (success) {
			updateCharacterData(characterId, personnage);
		}
		
		return personnage;
	}
	
	public Personnage passToNextPhase(CharacterId characterId, UserId userId) throws Exception {
		LoadedCharacter loadedCharacter = getCharacter(characterId, userId);
		Personnage personnage = loadedCharacter.getData();
		
		boolean success = personnage.passToNextPhase();
		if (success) {
			updateCharacterData(characterId, personnage);
		}
		
		return personnage;
	}
	
	

	
	private void updateCharacterData(CharacterId characterId, Personnage personnage) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PersonnageSaver.savePersonnage(personnage, baos);
		characterDao.updateData(characterId, baos.toByteArray());
	}

}
