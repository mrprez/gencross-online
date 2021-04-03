package com.mrprez.gencross.online.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mrprez.gencross.online.model.RpgTable;
import com.mrprez.gencross.online.model.id.UserId;

public interface RpgTableDao {
	
	List<RpgTable> getTablesAsGm(@Param("userId") UserId userId);

}
