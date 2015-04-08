package com.fredtm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration 
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "com.fredtm.api" ,"com.fredtm.api.controller" })
public class Config  {


	public static void main(String[] args) {
		SpringApplication.run(Config.class, args);
	}

//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		return null;
//	}
//
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return null;
//	}
//
//	@Override
//	protected String[] getServletMappings() {
//		return new String[]{"/fredtm-api/*"};
//	}
	
}
