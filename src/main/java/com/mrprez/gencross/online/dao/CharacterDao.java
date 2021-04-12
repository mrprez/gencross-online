package com.mrprez.gencross.online.dao;

import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.id.CharacterId;

public interface CharacterDao {
	
	void createCharacter(RpgCharacter character);

	RpgCharacter get(CharacterId characterId);

}
