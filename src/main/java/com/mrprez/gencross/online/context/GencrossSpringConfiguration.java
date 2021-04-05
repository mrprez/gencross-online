package com.mrprez.gencross.online.context;

import java.io.IOException;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mrprez.gencross.disk.PersonnageFactory;
import com.mrprez.gencross.online.dao.handler.TableIdTypeHandler;
import com.mrprez.gencross.online.dao.handler.UserIdTypeHandler;

@Configuration
@EnableWebMvc
@Import({ SpringSecurityConfiguration.class })
@ComponentScan(basePackages = { "com.mrprez.gencross.online.web", "com.mrprez.gencross.online.service" })
@MapperScan("com.mrprez.gencross.online.dao")
public class GencrossSpringConfiguration {

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("languages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setTypeHandlers(
				new UserIdTypeHandler(),
				new TableIdTypeHandler());
		return factoryBean.getObject();
	}
	
	@Bean
    public DataSource dataSource() throws NamingException {
        DataSource datasource = new JndiTemplate().lookup("java:comp/env/jdbc/gencross-db", DataSource.class);
        
        Flyway flyway = Flyway.configure().locations("classpath:db/migration").dataSource(datasource).load();
        flyway.migrate();
        
        return datasource;
    }
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersonnageFactory personnageFactory() throws IOException {
		return new PersonnageFactory();
	}

}
