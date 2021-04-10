package com.mrprez.gencross.online.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.online.dao.TableDao;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.TableWithCharacters;
import com.mrprez.gencross.online.model.id.UserId;

@Service
public class TableService {
	
	@Autowired
	private TableDao tableDao;

	@Autowired
	private DateProvider dateProvider;
	
	
	public List<TableWithCharacters> getUserGmTables(UserId userId) {
		return tableDao.getTablesAsGm(userId);
	}

	public void createTable(String name, String game, UserId userId) {
		Table table = new Table();
		table.setName(name);
		table.setGame(game);
		table.setGmId(userId);
		table.setCreationDate(dateProvider.now());
		tableDao.createTable(table);
	}

}
