package com.mrprez.gencross.online.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.assertj.core.api.Assertions;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.Test;

import com.mrprez.gencross.online.dbbuilder.UserTestDbBuilder;
import com.mrprez.gencross.online.model.User;
import com.mrprez.gencross.online.model.UserWithPassword;
import com.mrprez.gencross.online.model.id.UserId;

public class UserDaoTest extends AbstractDaoTest {
	
	private UserDao userDao;
	
	@Override
	protected void initDao(SqlSession session) {
		userDao = session.getMapper(UserDao.class);
	}
	
	@Override
	protected String getXmlFilePath() {
		return "com/mrprez/gencross/online/dao/UserDao.xml";
	}
	
	@Test
	public void createUser() throws DataSetException, SQLException {
		// GIVEN
		User user = new User();
		user.setUsername("myUsername");
		user.setEmail("myemail@test.com");
		
		// WHEN
		userDao.createUser(user, "myPasswordHash");
		
		// THEN
		Assertions.assertThat(user.getId()).isNotNull();
		ITable table = getDataSourceConnection().createQueryTable("user",
                "SELECT * FROM user u WHERE u.id="+user.getId().getInt()); 
		Assertions.assertThat(table.getRowCount()).isEqualTo(1);
		Assertions.assertThat(table.getValue(0, "username")).isEqualTo("myUsername");
		Assertions.assertThat(table.getValue(0, "email")).isEqualTo("myemail@test.com");
	}
	
	
	@Test
	public void updateUser() throws SQLException, DataSetException {
		// GIVEN
		UserId userId = UserTestDbBuilder.newUser("myUser1").withEmail("email1@test.com").withPasswordHash("myHash").save(getConnection());
		User user = new User();
		user.setId(userId);
		user.setUsername("myUser2");
		user.setEmail("email2@test.com");
		
		// WHEN
		userDao.updateUser(user);
		
		// THEN
		ITable table = getDataSourceConnection().createQueryTable("user",
                "SELECT * FROM user u WHERE u.id="+userId); 
		Assertions.assertThat(table.getValue(0, "username")).isEqualTo("myUser2");
		Assertions.assertThat(table.getValue(0, "email")).isEqualTo("email2@test.com");
		Assertions.assertThat(table.getValue(0, "password_hash")).isEqualTo("myHash");
	}
	
	
	@Test
	public void updatePassword() throws SQLException, DataSetException {
		// GIVEN
		UserId userId = UserTestDbBuilder.newUser("myUser").withPasswordHash("myHash1").save(getConnection());
		
		// WHEN
		userDao.updatePassword(userId, "myHash2");
		
		// THEN
		ITable table = getDataSourceConnection().createQueryTable("user",
                "SELECT * FROM user u WHERE u.id="+userId); 
		Assertions.assertThat(table.getValue(0, "password_hash")).isEqualTo("myHash2");
	}
	
	
	@Test
	public void get() throws SQLException {
		// GIVEN
		UserId userId = UserTestDbBuilder.newUser("myUser1").withEmail("email1@test.com").save(getConnection());
		
		// WHEN
		User user = userDao.get(userId);
		
		// THEN
		Assertions.assertThat(user.getId()).isEqualTo(userId);
		Assertions.assertThat(user.getUsername()).isEqualTo("myUser1");
		Assertions.assertThat(user.getEmail()).isEqualTo("email1@test.com");
	}
	
	
	@Test
	public void getWithPassword() throws SQLException {
		// GIVEN
		UserId userId = UserTestDbBuilder.newUser("myUser1").withEmail("email1@test.com").withPasswordHash("myHash1").save(getConnection());
		
		// WHEN
		UserWithPassword userWithPassword = userDao.getWithPassword("myUser1");
		
		// THEN
		Assertions.assertThat(userWithPassword.getUser().getId()).isEqualTo(userId);
		Assertions.assertThat(userWithPassword.getUser().getUsername()).isEqualTo("myUser1");
		Assertions.assertThat(userWithPassword.getUser().getEmail()).isEqualTo("email1@test.com");
		Assertions.assertThat(userWithPassword.getHash()).isEqualTo("myHash1");
	}

}
