package com.fredtm.data;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Configuration
@ActiveProfiles(profiles = "dev,test,prod")
@ComponentScan(basePackages = {"com.fredtm.core.model","com.fredtm.data"})
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableJpaRepositories(basePackages = { "com.fredtm.core.model",
		"com.fredtm.data", "com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")
@EntityScan(basePackages = { "com.fredtm.core.model" })
public class FredTmDataConfig {

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.indentOutput(true).dateFormat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		builder.configure(objectMapper);
		return builder;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(FredTmDataConfig.class, args);
	}
}
