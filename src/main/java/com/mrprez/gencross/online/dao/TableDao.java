package com.mrprez.gencross.online.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.id.UserId;

public interface TableDao {
	
	List<Table> getTablesAsGm(@Param("userId") UserId userId);
	
	void createTable(Table table);

}
