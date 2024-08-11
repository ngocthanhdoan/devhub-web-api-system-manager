package com.manager.web.app.api.trx;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api")
public class ApiGenericController {

	@Autowired
	private org.springframework.context.ApplicationContext applicationContext;

	@GetMapping("/list")
	public Map<String, String> getRepositories() {
		Map<String, Repository> repositories = applicationContext.getBeansOfType(Repository.class);
		return repositories.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getClass().getName()));
	}

	@GetMapping("/{entityName}")
	public Page<?> listEntities(@PathVariable String entityName,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
		Map<String, Repository> repositories = applicationContext.getBeansOfType(Repository.class);
		JpaRepository<?, ?> repository = (JpaRepository<?, ?>) repositories.get(entityName);
		if (repository == null) {
			throw new Exception("No repository found for entity class " + entityName);
		}
		Pageable pageable = PageRequest.of(page, size);
		return repository.findAll(pageable);
	}

	@PostMapping("/{entityName}")
	public <T> T createEntity(@PathVariable String entityName, @RequestBody T entity) throws Exception {
		JpaRepository<T, Serializable> repository = (JpaRepository<T, Serializable>) getRepository(entityName);
		return repository.save(entity);
	}

	@PutMapping("/{entityName}/{id}")
	public <T> T updateEntity(@PathVariable String entityName, @PathVariable Serializable id,
			@RequestBody T updatedEntity) throws Exception {
		JpaRepository<T, Serializable> repository = (JpaRepository<T, Serializable>) getRepository(entityName);
		if (!repository.existsById(id)) {
			throw new Exception("Entity not found with id " + id);
		}
		return repository.save(updatedEntity);
	}
	@DeleteMapping("/{entityName}/{id}")
	public void deleteEntity(@PathVariable String entityName, @PathVariable Serializable id) throws Exception {
		JpaRepository<?, Serializable> repository = getRepository(entityName);
		if (!repository.existsById(id)) {
			throw new Exception("Entity not found with id " + id);
		}
		repository.deleteById(id);
	}

	private JpaRepository<?, Serializable> getRepository(String entityName) throws Exception {
		Map<String, Repository> repositories = applicationContext.getBeansOfType(Repository.class);
		JpaRepository<?, Serializable> repository = (JpaRepository<?, Serializable>) repositories.get(entityName);
		if (repository == null) {
			throw new Exception("No repository found for entity class " + entityName);
		}
		return repository;
	}
}
