package com.mrprez.gencross.online.dao;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.id.CharacterId;

public interface CharacterDao {
	
	void createCharacter(RpgCharacter character);

	RpgCharacter get(CharacterId characterId);
	
	void updateData(@Param("id") CharacterId characterId, @Param("data") byte[] data);

}
