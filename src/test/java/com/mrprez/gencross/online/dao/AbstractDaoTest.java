package com.mrprez.gencross.online.dao;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.ClassPathResource;

import com.mrprez.gencross.online.context.GencrossSpringConfiguration;

public abstract class AbstractDaoTest {
	
	private JdbcDataSource dataSource;
	
	
	@BeforeEach
	public void initDatabase() throws Exception {
		File databaseFolder = Files.createTempDirectory(null).toFile();
		dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:"+databaseFolder.getAbsolutePath()+"/gencross");
        dataSource.setUser("sa");
        dataSource.setPassword("sa");
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setTypeHandlers(GencrossSpringConfiguration.getTypeHandlers());
		factoryBean.setMapperLocations(new ClassPathResource(getXmlFilePath()));
		SqlSession session = factoryBean.getObject().openSession();
		initDao(session);
	}
	
	protected abstract void initDao(SqlSession session);
	
	protected abstract String getXmlFilePath();
	
	protected DatabaseDataSourceConnection getDataSourceConnection() throws SQLException {
		return new DatabaseDataSourceConnection(dataSource);
	}
	
	protected Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
