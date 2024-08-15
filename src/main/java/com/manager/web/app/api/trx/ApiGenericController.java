package com.manager.web.app.api.trx;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;

@RestController
@RequestMapping("/v2/api")
public class ApiGenericController {

	@Autowired
	private org.springframework.context.ApplicationContext applicationContext;

	 @Autowired
    private ObjectMapper objectMapper;
	private String formatKey(String key) {
        // Loại bỏ "Repositories" và upper chữ cái đầu tiên của key
        key = key.replace("Repository", "");
        if (key.isEmpty()) {
            return key;
        }
        return key.substring(0, 1).toUpperCase() + key.substring(1);
    }
	@GetMapping("/list")
	public Map<Object, Object> getRepositories() {
		Map<String, Repository> repositories = applicationContext.getBeansOfType(Repository.class);

		// return repositories.entrySet().stream()
		// 		.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getClass().getName()));
		return repositories.entrySet().stream().collect(Collectors.toMap(
			entry -> formatKey(entry.getKey()), // Format key như bạn đã yêu cầu
			entry -> {
				try {
					// Lấy class theo tên đã được format
					Class<?> clazz = Class.forName("com.manager.web.app.api.vo." + formatKey(entry.getKey()));
					
					// Tạo instance của class
					//Object instance = clazz.getDeclaredConstructor().newInstance();

					// Chuyển đổi đối tượng thành chuỗi JSON
					//return objectMapper.writeValueAsString(instance);
				return getFieldTypes(clazz);
				} catch (Exception e) {
					e.printStackTrace();
					return "{}"; // Trả về chuỗi JSON rỗng nếu có lỗi
				}
			}
	));
	}
	 /**
     * Tạo một Map ánh xạ tên các trường (key) với kiểu dữ liệu của chúng (value) của một lớp.
     * @param clazz lớp cần xử lý
     * @return một Map chứa tên trường và kiểu dữ liệu của chúng
     */
    public static Map<String, Class<?>> getFieldTypes(Class<?> clazz) {
        Map<String, Class<?>> fieldTypes = new HashMap<>();
        
        // Lấy tất cả các trường (fields) của lớp
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // Lấy tên trường và kiểu dữ liệu của nó
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            
            // Thêm vào Map
            fieldTypes.put(fieldName, fieldType);
        }
        
        return fieldTypes;
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
	@GetMapping("/{entityName}/{id}")
	public Optional<?> listEntities(@PathVariable String entityName,@PathVariable Serializable id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
		JpaRepository<?, Serializable> repository = (JpaRepository<?, Serializable>) getRepository(entityName);
		return repository.findById(id);
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
	@GetMapping("/api-doc")
	@ResponseBody
    public String getOpenApiDocumentation() throws Exception {
        java.util.List<Map<String, String>> apiList = java.util.List.of(
            Map.of("endpoint", "/users", "method", "GET", "description", "Retrieve all users", "parameters", "None"),
            Map.of("endpoint", "/users/{id}", "method", "GET", "description", "Retrieve user by ID", "parameters", "id: integer")
        );

        OpenAPI openAPI = new OpenAPI()
            .info(new Info().title("Dynamic API Documentation").version("1.0"));

        Paths paths = new Paths();

        for (Map<String, String> api : apiList) {
            String endpoint = api.get("endpoint");
            String method = api.get("method");
            String description = api.get("description");
            String parameters = api.get("parameters");

            io.swagger.v3.oas.models.PathItem pathItem = new io.swagger.v3.oas.models.PathItem()
                .description(description);

            io.swagger.v3.oas.models.Operation operation = new io.swagger.v3.oas.models.Operation()
                .summary(description)
                .description(parameters);

            switch (method.toUpperCase()) {
                case "GET":
                    pathItem.get(operation);
                    break;
                case "POST":
                    pathItem.post(operation);
                    break;
                // Add other methods as needed
            }

            paths.addPathItem(endpoint, pathItem);
        }

        openAPI.setPaths(paths);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(openAPI);
    }
}
