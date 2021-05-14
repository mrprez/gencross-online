package com.mrprez.gencross.online.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.Test;

import com.mrprez.gencross.online.dbbuilder.TableTestDbBuilder;
import com.mrprez.gencross.online.dbbuilder.UserTestDbBuilder;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public class TableDaoTest extends AbstractDaoTest {
	
	private TableDao tableDao;
	
	@Override
	protected void initDao(SqlSession session) {
		tableDao = session.getMapper(TableDao.class);
	}
	
	@Override
	protected String getXmlFilePath() {
		return "com/mrprez/gencross/online/dao/TableDao.xml";
	}
	
	@Test
	public void get() throws DataSetException, SQLException {
		// GIVEN
		UserId gmId = UserTestDbBuilder.newUser("myGm").save(getConnection());
		TableId tableId = TableTestDbBuilder.newTable("myTable").withGm(gmId).withGame("myGame").withCreationDate("2021-04-23T15:47:51").save(getConnection());
		
		// WHEN
		Table table = tableDao.get(tableId);
		
		// THEN
		ITable queryTable = getDataSourceConnection().createQueryTable("rpg_table",
                "SELECT * FROM rpg_table rt WHERE rt.id="+table.getId()); 
		Assertions.assertThat(queryTable.getRowCount()).isEqualTo(1);
		Assertions.assertThat(queryTable.getValue(0, "name")).isEqualTo("myTable");
		Assertions.assertThat(queryTable.getValue(0, "game")).isEqualTo("myGame");
		Assertions.assertThat(queryTable.getValue(0, "gm_id")).isEqualTo(gmId.getInt());
		Assertions.assertThat((Timestamp) queryTable.getValue(0, "creation_date")).isEqualToIgnoringMillis("2021-04-23T15:47:51");
	}
	
	@Test
	public void createTable() throws SQLException {
		// GIVEN
		UserId gmId = UserTestDbBuilder.newUser("myGm").save(getConnection());
		Table table = new Table();
		table.setName("myName");
		table.setGame("myGame");
		table.setGmId(gmId);
		table.setCreationDate(LocalDateTime.parse("2021-04-23T16:23:55"));
		
		// WHEN
		tableDao.createTable(table);
		
		// THEN
		Assertions.assertThat(table.getId()).isNotNull();
		Assertions.assertThat(table.getName()).isEqualTo("myName");
		Assertions.assertThat(table.getGame()).isEqualTo("myGame");
		Assertions.assertThat(table.getGmId()).isEqualTo(gmId);
		Assertions.assertThat(table.getCreationDate()).isEqualTo("2021-04-23T16:23:55");
	}

}
