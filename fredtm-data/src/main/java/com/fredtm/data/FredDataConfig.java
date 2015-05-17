package com.fredtm.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.fredtm.core.model",
		"com.fredtm.data", "com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")

public class FredDataConfig {

	public static void main(String[] args) {
		SpringApplication.run(FredDataConfig.class, args);
	}
}
