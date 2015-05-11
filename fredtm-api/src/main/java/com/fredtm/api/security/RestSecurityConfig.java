package com.fredtm.api.security;

import groovy.util.logging.Slf4j;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Slf4j
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource ds;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		 auth.jdbcAuthentication()
		 .dataSource(ds)
		
		 .usersByUsernameQuery(
		 "select email as username, password_hash as password,1 as enabled from account where email = ?")
		 .authoritiesByUsernameQuery(
		 "select email as username,'ROLE_USER' as authority from account where email = ?");

//		auth.inMemoryAuthentication().withUser("leo.zapparoli@gmail.com")
//				.password("123").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**").hasRole("USER").and()
				.httpBasic();
		http.csrf().disable();

	}

}
