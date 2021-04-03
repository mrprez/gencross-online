package com.mrprez.gencross.online.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrprez.gencross.online.dao.RpgTableDao;
import com.mrprez.gencross.online.model.RpgTable;
import com.mrprez.gencross.online.model.id.UserId;

@Service
public class TableService {
	
	@Autowired
	private RpgTableDao rpgTableDao;
	
	public List<RpgTable> getUserGmTables(UserId userId) {
		return rpgTableDao.getTablesAsGm(userId);
	}

}
