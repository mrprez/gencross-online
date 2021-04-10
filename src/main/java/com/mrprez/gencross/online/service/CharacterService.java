package com.mrprez.gencross.online.service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.Personnage;
import com.mrprez.gencross.disk.PersonnageFactory;
import com.mrprez.gencross.disk.PersonnageSaver;
import com.mrprez.gencross.disk.PluginDescriptor;
import com.mrprez.gencross.online.dao.CharacterDao;
import com.mrprez.gencross.online.dao.TableDao;
import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

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
			throw new IllegalAccessError();
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

}
