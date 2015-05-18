package com.fredtm.data;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("prod")
@Configuration
@EnableJpaRepositories(basePackages = { "com.fredtm.data",
		"com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = { "com.fredtm.data.repository",
		"com.fredtm.service" })
@EnableTransactionManagement
public class ProdDBConfig {

	@Bean
	@Profile("prod")
	LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
		sf.setDataSource(generateDataSource());
		sf.setPackagesToScan(new String[] { "com.fredtm.core.model" });
		sf.setHibernateProperties(additionalProperties());
		return sf;
	}

	@Bean
	@Profile("prod")
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(generateDataSource());
		em.setPackagesToScan(new String[] { "com.fredtm.core.model" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		return em;
	}

	@Bean
	@Profile("prod")
	public DataSource generateDataSource() {
		// DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// dataSource.setDriverClassName("org.postgresql.Driver");
		// dataSource.setUrl("jdbc:postgres://zmjlnczlkkcehh:MMuYhUtsGMClWFfTLy_ZiVmqg0@ec2-184-73-253-4.compute-1.amazonaws.com:5432/d6jhi09e6pcqvq");
		// dataSource.setUsername("zmjlnczlkkcehh");
		// dataSource.setPassword("MMuYhUtsGMClWFfTLy_ZiVmqg0");
		// return dataSource;

		URI dbUri = null;
		try {
			dbUri = new URI(System.getenv("DATABASE_URL"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
				+ dbUri.getPort() + dbUri.getPath();

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		return basicDataSource;
	}

	@Bean
	@Profile("prod")
	PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Profile("prod")
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create");
		properties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("show_sql", "true");
		return properties;
	}

}
