package com.manager.web.app.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.sql2o.Sql2o;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Component
public class DataSetConfig {

    private final Environment env;

    public DataSetConfig(Environment env) {
        this.env = env;
    }

//    @Bean
    public Sql2o sql2o() {
       try{
        String dbUrl = env.getProperty("spring.datasource.url");
        String dbUsername = env.getProperty("spring.datasource.username");
        String dbPassword = env.getProperty("spring.datasource.password");
        return new Sql2o(dbUrl, dbUsername, dbPassword);
       }catch(Exception e){
        
       }
       return null;
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
