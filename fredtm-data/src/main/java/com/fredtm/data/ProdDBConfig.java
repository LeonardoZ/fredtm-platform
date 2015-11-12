package com.fredtm.data;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
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

import com.jolbox.bonecp.BoneCPDataSource;

@Profile("prod")
@Configuration
@EnableJpaRepositories(basePackages = { "com.fredtm.data",
		"com.fredtm.data.repository" }, transactionManagerRef = "transactionManager")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = { "com.fredtm.data.repository", "com.fredtm.service" })
@EnableTransactionManagement
public class ProdDBConfig {

	@Value("${bonecp.driverClass}")
	private String driverClass;

	@Value("${bonecp.idleMaxAgeInMinutes}")
	private Integer idleMaxAgeInMinutes;

	@Value("${bonecp.idleConnectionTestPeriodInMinutes}")
	private Integer idleConnectionTestPeriodInMinutes;

	@Value("${bonecp.maxConnectionsPerPartition}")
	private Integer maxConnectionsPerPartition;

	@Value("${bonecp.minConnectionsPerPartition}")
	private Integer minConnectionsPerPartition;

	@Value("${bonecp.partitionCount}")
	private Integer partitionCount;

	@Value("${bonecp.acquireIncrement}")
	private Integer acquireIncrement;

	@Value("${bonecp.statementsCacheSize}")
	private Integer statementsCacheSize;

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

	@Bean(destroyMethod = "close")
	@Profile("prod")
	public DataSource generateDataSource() {

		String dbUri = null;
		try {
			dbUri = System.getenv("FRED_DATABASE_URL");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// host:port:user:password
		String[] splited = dbUri.split(":");
		String host = splited[0];
		String port = splited[1];
		String username = splited[2];
		String password = splited[3];
		String dbUrl = "jdbc:mysql://" + host + ':' + port + "/fredtm?useUnicode=yes&characterEncoding=UTF-8";
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(driverClass);
		dataSource.setJdbcUrl(dbUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setIdleConnectionTestPeriodInMinutes(idleConnectionTestPeriodInMinutes);
		dataSource.setIdleMaxAgeInMinutes(idleMaxAgeInMinutes);
		dataSource.setMaxConnectionsPerPartition(maxConnectionsPerPartition);
		dataSource.setMinConnectionsPerPartition(minConnectionsPerPartition);
		dataSource.setPartitionCount(partitionCount);
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setStatementsCacheSize(statementsCacheSize);
		return dataSource;
	}

	@Bean
	@Profile("prod")
	PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Profile("prod")
	Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "create-update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		return properties;
	}

}
