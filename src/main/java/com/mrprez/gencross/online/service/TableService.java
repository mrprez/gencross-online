package com.mrprez.gencross.online.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.online.dao.TableDao;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.aggregation.TableWithCharactersAndPlayers;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

@Service
public class TableService {
	
	@Autowired
	private TableDao tableDao;

	@Autowired
	private DateProvider dateProvider;
	
	
	public List<TableWithCharactersAndPlayers> getUserGmTables(UserId userId) {
		return tableDao.getTablesAsGm(userId);
	}

	public Table createTable(String name, String game, UserId userId) {
		Table table = new Table();
		table.setName(name);
		table.setGame(game);
		table.setGmId(userId);
		table.setCreationDate(dateProvider.now());
		tableDao.createTable(table);
		return table;
	}

	public TableWithCharactersAndPlayers getTableAsGm(TableId tableId, UserId userId) {
		return tableDao.getTableAsGm(tableId, userId);
	}

}
