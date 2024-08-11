package com.manager.web.app.api.component;
import java.util.Map;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class RepositoryLister {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void listRepositories() {
        ListableBeanFactory beanFactory = (ListableBeanFactory) applicationContext;
        Map<String, Repository> repositories = beanFactory.getBeansOfType(Repository.class);
        repositories.forEach((name, repo) -> {
            System.out.println("Repository bean name: " + name);
            System.out.println("Repository bean class: " + repo.getClass().getName());
        });
    }
}
