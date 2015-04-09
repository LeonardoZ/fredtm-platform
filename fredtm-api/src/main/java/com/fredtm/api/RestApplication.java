package com.fredtm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fredtm.data.repository.OperationRepository;

@Configuration 
@EnableJpaRepositories(transactionManagerRef="transactionManager")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "com.fredtm.data","com.fredtm.data.repository","com.fredtm.api" ,"com.fredtm.api.controller" })
public class RestApplication  {
	
	@Autowired
	OperationRepository repo;


	public static void main(String[] args) {
		SpringApplication.run(RestApplication.class, args);
	}

}
