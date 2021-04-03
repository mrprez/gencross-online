package com.mrprez.gencross.online.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/**/*.js").permitAll()
				.antMatchers("/**/*.css").permitAll()
				.antMatchers("/**/*.png").permitAll()
				.antMatchers("/dispatcher/createAccount*").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().hasRole("USER")
			.and()
				.formLogin()
				.loginPage("/dispatcher/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dispatcher/home", true)
				.permitAll()
			.and().csrf().disable();
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
	
}
