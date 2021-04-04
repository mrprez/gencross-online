package com.mrprez.gencross.online.dao.handler;


import org.apache.ibatis.type.MappedTypes;

import com.mrprez.gencross.online.model.id.TableId;

@MappedTypes(TableId.class)
public class TableIdTypeHandler extends AbstractIntIdTypeHandler<TableId> {

	@Override
	protected TableId newId(int i) {
		return new TableId(i);
	}

}
