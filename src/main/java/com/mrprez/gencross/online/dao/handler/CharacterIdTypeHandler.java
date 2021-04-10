package com.mrprez.gencross.online.dao.handler;

import org.apache.ibatis.type.MappedTypes;

import com.mrprez.gencross.online.model.id.CharacterId;


@MappedTypes(CharacterId.class)
public class CharacterIdTypeHandler extends AbstractIntIdTypeHandler<CharacterId> {

	@Override
	protected CharacterId newId(int idAsInt) {
		return new CharacterId(idAsInt);
	}

}
