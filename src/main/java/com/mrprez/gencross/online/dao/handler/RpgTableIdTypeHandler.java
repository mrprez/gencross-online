package com.mrprez.gencross.online.dao.handler;


import org.apache.ibatis.type.MappedTypes;

import com.mrprez.gencross.online.model.id.RpgTableId;

@MappedTypes(RpgTableId.class)
public class RpgTableIdTypeHandler extends AbstractIntIdTypeHandler<RpgTableId> {

	@Override
	protected RpgTableId newId(int i) {
		return new RpgTableId(i);
	}

}
