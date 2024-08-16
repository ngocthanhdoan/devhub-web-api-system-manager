package com.manager.web.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.manager.web.app.abs.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DataSetConfig {

	@PersistenceContext
	private EntityManager entityManager;

	@Bean
	public HibernateUtil dataSet() {
		HibernateUtil dataSet = new HibernateUtil();
		dataSet.setEntityManager(entityManager);
		return dataSet;
	}
}

// import org.sql2o.Sql2o;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class DataSetConfig {

//     @Value("${spring.datasource.url}")
//     private String dbUrl;

//     @Value("${spring.datasource.username}")
//     private String dbUsername;

//     @Value("${spring.datasource.password}")
//     private String dbPassword;

//     @Value("${spring.datasource.driver-class-name}")
//     private String dbDriverClassName;

//     @Bean
//     public Sql2o sql2o() {
//         return new Sql2o(dbUrl, dbUsername, dbPassword);
//     }
// }
