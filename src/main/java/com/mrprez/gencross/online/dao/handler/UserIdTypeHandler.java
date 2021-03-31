package com.mrprez.gencross.online.dao.handler;


import org.apache.ibatis.type.MappedTypes;

import com.mrprez.gencross.online.model.id.UserId;

@MappedTypes(UserId.class)
public class UserIdTypeHandler extends AbstractIntIdTypeHandler<UserId> {

	@Override
	protected UserId newId(int i) {
		return new UserId(i);
	}

}
