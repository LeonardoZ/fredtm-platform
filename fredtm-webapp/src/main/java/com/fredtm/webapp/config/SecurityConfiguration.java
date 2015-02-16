package com.fredtm.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/**").hasRole("USER")
				.and()
				.formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/main")
				.failureUrl("/login?error=true");
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
//		auth.inMemoryAuthentication()
//			.withUser("leo")
//			.password("asd")
//			.roles("USER");
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select user as username,pass as password,true as enabled from users where user = ?")
		.passwordEncoder(new Md5PasswordEncoder())
		.authoritiesByUsernameQuery("select users.id,'ROLE_USER' as authority from users where user = ?")
		
		;
	}
}
