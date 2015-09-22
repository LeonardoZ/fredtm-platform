package com.fredtm.api;

import java.text.SimpleDateFormat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fredtm.api.security.JwtFilter;

@Configuration
@EnableJpaRepositories(basePackages = { "com.fredtm.data",
		"com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HypermediaAutoConfiguration.class })
@ComponentScan(basePackages = { "com.fredtm.core.model", "com.fredtm.data", "com.fredtm.data.repository",
		"com.fredtm.service", "com.fredtm.api", "com.fredtm.api.rest", "com.fredtm.api.resource" })
@ActiveProfiles(profiles = "dev,test,prod")
@EnableEntityLinks
public class FredTmApiConfig {


	  @Bean
	    public FilterRegistrationBean jwtFilter() {
	        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	        registrationBean.setFilter(new JwtFilter());
	        registrationBean.addUrlPatterns("/*");
	        registrationBean.setOrder(10);
	        return registrationBean;
	    }

	
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
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "9000";
		}
		SpringApplication.run(FredTmApiConfig.class, args);
	}

}
