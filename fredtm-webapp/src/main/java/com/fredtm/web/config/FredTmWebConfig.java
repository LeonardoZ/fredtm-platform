package com.fredtm.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.fredtm.web.controller")
@EnableAutoConfiguration
public class FredTmWebConfig {
	
	public static void main(String[] args) {
		SpringApplication.run(FredTmWebConfig.class, args);
	}

}
