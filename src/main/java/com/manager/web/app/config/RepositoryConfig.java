package com.manager.web.app.config;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = "com.your.package", includeFilters = @ComponentScan.Filter(Repository.class))
public class RepositoryConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void listRepositories() {
        Map<String, Object> repositoryBeans = applicationContext.getBeansWithAnnotation(Repository.class);
        repositoryBeans.forEach((name, bean) -> {
            System.out.println("Repository bean name: " + name);
            System.out.println("Repository bean class: " + bean.getClass().getName());
        });
    }
}
