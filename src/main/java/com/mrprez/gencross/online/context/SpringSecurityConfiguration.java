package com.mrprez.gencross.online.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.mrprez.gencross.online.service.GencrossAuthenticationProvider;
import com.mrprez.gencross.online.web.GencrossAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private GencrossAuthenticationProvider authenticationProvider;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/**/*.js").permitAll()
				.antMatchers("/**/*.css").permitAll()
				.antMatchers("/**/*.png").permitAll()
				.antMatchers("/**/*.svg").permitAll()
				.antMatchers(HttpMethod.GET, "/dispatcher/login").permitAll()
				.antMatchers(HttpMethod.GET, "/dispatcher/account/create").permitAll()
				.antMatchers(HttpMethod.POST, "/dispatcher/account").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().hasRole("USER")
			.and().formLogin()
				.loginProcessingUrl("/login")
				.failureUrl("/dispatcher/login?error")
				.defaultSuccessUrl("/dispatcher/home", true)
				.permitAll()
			.and().logout().logoutSuccessUrl("/dispatcher/login")
			.and().exceptionHandling()
				.authenticationEntryPoint(new GencrossAuthenticationEntryPoint("/dispatcher/login"));
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
