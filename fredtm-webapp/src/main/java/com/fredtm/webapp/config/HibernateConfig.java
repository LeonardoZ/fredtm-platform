package com.fredtm.webapp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Bean
	LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(generateDataSource());
		sf.setPackagesToScan(new String[] { "com.fredtm.core.model" });

		sf.setHibernateProperties(additionalProperties());
		return sf;
	}

	@Bean
	PlatformTransactionManager defaultTransactionManager(SessionFactory factory) {
		HibernateTransactionManager htm = new HibernateTransactionManager(
				factory);
		htm.setDataSource(generateDataSource());
		return htm;
	}

	// @Bean
	// LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	// LocalContainerEntityManagerFactoryBean em = new
	// LocalContainerEntityManagerFactoryBean();
	// em.setDataSource(generateDataSource());
	// em.setPackagesToScan(new String[] { "com.fredtm.core.model" });
	//
	// JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	// em.setJpaVendorAdapter(vendorAdapter);
	// em.setJpaProperties(additionalProperties());
	// return em;
	// }

	@Bean
	DataSource generateDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/fredtm");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	@Bean
	PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("show_sql", "true");
		return properties;
	}

}
