package com.manager.web.app.api.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryMap {

	@Autowired
	private ApplicationContext applicationContext;

	private final Map<Class<?>, JpaRepository<?, ?>> repositoryMap = new HashMap<>();

	@jakarta.annotation.PostConstruct
	private void init() {
		// Lấy tất cả các bean JpaRepository từ ApplicationContext
		Map<String, JpaRepository> repositories = applicationContext.getBeansOfType(JpaRepository.class);
		for (JpaRepository<?, ?> repository : repositories.values()) {
			// Tìm domain class từ repository
			Class<?> domainClass = getDomainClassFromRepository(repository.getClass());
			if (domainClass != null) {
				repositoryMap.put(domainClass, repository);
			}
		}
	}

	// Phương thức để lấy domain class từ repository
	private Class<?> getDomainClassFromRepository(Class<?> repositoryClass) {
		// Giả sử tên repository là "UserRepository" và entity class là "User"
		String domainClassName = repositoryClass.getSimpleName().replace("Repository", "");
		try {
			return Class.forName("com.manager.web.app.api.repository." + domainClassName);
		} catch (ClassNotFoundException e) {
			return null; // Hoặc xử lý lỗi nếu không tìm thấy domain class
		}
	}

	// Phương thức để lấy repository theo domain class
	public <T, ID extends Serializable> JpaRepository<T, ID> getRepository(Class<T> domainClass) {
		return (JpaRepository<T, ID>) repositoryMap.get(domainClass);
	}
}
