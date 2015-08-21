package com.fredtm.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;


@Configuration
@ActiveProfiles(profiles = "dev,test,prod")
@ComponentScan(basePackages = {"com.fredtm.core.model","com.fredtm.data"})
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableJpaRepositories(basePackages = { "com.fredtm.core.model",
		"com.fredtm.data", "com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")
@EntityScan(basePackages = { "com.fredtm.core.model" })
public class FredTmDataConfig {

	public static void main(String[] args) {
		SpringApplication.run(FredTmDataConfig.class, args);
	}
}
