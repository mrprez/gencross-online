package com.mrprez.gencross.online.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mrprez.gencross.online.dbbuilder.CharacterTestDbBuilder;
import com.mrprez.gencross.online.dbbuilder.TableTestDbBuilder;
import com.mrprez.gencross.online.dbbuilder.UserTestDbBuilder;
import com.mrprez.gencross.online.model.RpgCharacter;
import com.mrprez.gencross.online.model.RpgCharacterWithTable;
import com.mrprez.gencross.online.model.Table;
import com.mrprez.gencross.online.model.id.CharacterId;
import com.mrprez.gencross.online.model.id.TableId;
import com.mrprez.gencross.online.model.id.UserId;

public class CharacterDaoTest extends AbstractDaoTest {
	
	private CharacterDao characterDao;
	

	@Override
	protected void initDao(SqlSession session) {
		characterDao = session.getMapper(CharacterDao.class);
	}

	@Override
	protected Resource[] getXmlFileResources() {
		return new Resource[] {
				new ClassPathResource("com/mrprez/gencross/online/dao/TableDao.xml"),
				new ClassPathResource("com/mrprez/gencross/online/dao/CharacterDao.xml")
		};
	}
	
	@Test
	public void getRpgCharacterWithTable() throws Exception {
		// GIVEN
		UserId gmId = UserTestDbBuilder.newUser("myGm").save(getConnection());
		TableId tableId = TableTestDbBuilder.newTable("myTable").withGm(gmId).save(getConnection());
		UserId playerId = UserTestDbBuilder.newUser("myPlayer").save(getConnection());
		CharacterId characterId = CharacterTestDbBuilder.newCharacter("myCharacter", tableId)
				.withPlayer(playerId).withCreationDate(LocalDateTime.parse("2021-05-06T11:17:20")).withData("myData".getBytes())
				.save(getConnection());
		
		// WHEN
		RpgCharacterWithTable characterWithTable = characterDao.getRpgCharacterWithTable(characterId);
		
		// THEN
		Table table = characterWithTable.getTable();
		Assertions.assertThat(table.getGmId()).isEqualTo(gmId);
		Assertions.assertThat(table.getName()).isEqualTo("myTable");
		
		RpgCharacter character = characterWithTable.getRpgCharacter();
		Assertions.assertThat(character.getId()).isEqualTo(characterId);
		Assertions.assertThat(character.getName()).isEqualTo("myCharacter");
		Assertions.assertThat(character.getTableId()).isEqualTo(tableId);
		Assertions.assertThat(character.getData()).isEqualTo("myData".getBytes());
		Assertions.assertThat(character.getCreationDate()).isEqualToIgnoringNanos(LocalDateTime.parse("2021-05-06T11:17:20"));
		Assertions.assertThat(character.getPlayerId()).isEqualTo(playerId);
	}
	
	@Test
	public void createUser() throws DataSetException, SQLException {
		// GIVEN
		TableId tableId = TableTestDbBuilder.newTable("myTable").save(getConnection());
		RpgCharacter character = new RpgCharacter();
		character.setName("myCharacter");
		character.setTableId(tableId);
		character.setCreationDate(LocalDateTime.parse("2021-05-06T14:29:20"));
		character.setData("myData".getBytes());
		
		// WHEN
		characterDao.createCharacter(character);
		
		// THEN
		Assertions.assertThat(character.getId()).isNotNull();
		ITable table = getDataSourceConnection().createQueryTable("rpg_character",
                "SELECT * FROM rpg_character rc WHERE rc.id="+character.getId().getInt()); 
		Assertions.assertThat(table.getRowCount()).isEqualTo(1);
		Assertions.assertThat(table.getValue(0, "name")).isEqualTo("myCharacter");
		Assertions.assertThat(table.getValue(0, "table_id")).isEqualTo(tableId.getInt());
		Assertions.assertThat(table.getValue(0, "data")).isEqualTo("myData".getBytes());
		Assertions.assertThat(table.getValue(0, "creation_date")).isEqualTo(Timestamp.valueOf("2021-05-06 14:29:20"));
	}
	
	@Test
	public void updateData() throws Exception {
		// GIVEN
		TableId tableId = TableTestDbBuilder.newTable("myTable").save(getConnection());
		CharacterId characterId = CharacterTestDbBuilder.newCharacter("myCharacter", tableId).save(getConnection());
		
		// WHEN
		characterDao.updateData(characterId, "newData".getBytes());
		
		// THEN
		ITable table = getDataSourceConnection().createQueryTable("rpg_character",
                "SELECT * FROM rpg_character rc WHERE rc.id="+characterId.getInt()); 
		Assertions.assertThat(table.getRowCount()).isEqualTo(1);
		Assertions.assertThat(table.getValue(0, "data")).isEqualTo("newData".getBytes());
	}
	

}
