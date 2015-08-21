package com.fredtm.data;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.fredtm.data",
		"com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")
@EnableAutoConfiguration()
@ComponentScan(basePackages = { "com.fredtm.data.repository",
		"com.fredtm.service" })
@EnableTransactionManagement
public class TestDBConfig {

	@Bean
	@Profile("test")
	LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(generateTestDataSource());
		sf.setPackagesToScan(new String[] { "com.fredtm.core.model" });
		sf.setHibernateProperties(additionalProperties());
		return sf;
	}

	@Bean
	@Profile("test")
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(generateTestDataSource());
		em.setPackagesToScan(new String[] { "com.fredtm.core.model" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	@Profile("test")
	DataSource generateTestDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl("jdbc:hsqldb:hsql//localhost/");
		dataSource.setUsername("sa");
		dataSource.setPassword("sa");
		return dataSource;
	}

	@Bean
	@Profile("test")
	PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Profile("test")
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.HSQLDialect");
		properties.setProperty("show_sql", "true");
		return properties;
	}

}
