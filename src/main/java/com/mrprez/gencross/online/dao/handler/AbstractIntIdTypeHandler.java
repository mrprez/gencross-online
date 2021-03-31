package com.mrprez.gencross.online.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.mrprez.gencross.online.model.id.AbstractIntId;

public abstract class AbstractIntIdTypeHandler<T extends AbstractIntId> implements TypeHandler<T>{

	@Override
	public void setParameter(PreparedStatement ps, int paramaterIndex, T id, JdbcType jdbcType)
			throws SQLException {
		if (id == null) {
			ps.setNull(paramaterIndex, Types.INTEGER);
		} else {
			ps.setInt(paramaterIndex, id.getInt());
		}
	}

	@Override
	public T getResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		if (i==0 && rs.wasNull()) {
			return null;
		}
		return newId(i);
	}

	@Override
	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
		if (i==0 && rs.wasNull()) {
			return null;
		}
		return newId(i);
	}

	@Override
	public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		if (i==0 && cs.wasNull()) {
			return null;
		}
		return newId(i);
	}
	
	protected abstract T newId(int i);

}
