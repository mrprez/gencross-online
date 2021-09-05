package com.mrprez.gencross.online.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.aggregation.TableWithCharactersAndPlayers;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public interface TableDao {
	
	List<TableWithCharactersAndPlayers> getTablesAsGm(@Param("userId") UserId userId);
	
	void createTable(Table table);

	Table get(@Param("tableId") TableId tableId);

	TableWithCharactersAndPlayers getTableAsGm(@Param("tableId") TableId tableId, @Param("userId") UserId userId);

}
