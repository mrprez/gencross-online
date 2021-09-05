package com.mrprez.gencross.online.dao;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.RpgCharacterWithTable;
import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.UserId;

public interface CharacterDao {
	
	void createCharacter(RpgCharacter character);

	RpgCharacterWithTable getRpgCharacterWithTable(@Param("id") CharacterId characterId);
	
	void updateData(@Param("id") CharacterId characterId, @Param("data") byte[] data);
	
	void attributeToPlayer(@Param("id") CharacterId characterId, @Param("playerId") UserId playerId);

}
