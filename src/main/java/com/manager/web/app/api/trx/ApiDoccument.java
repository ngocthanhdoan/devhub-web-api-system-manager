package com.manager.web.app.api.trx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manager.web.app.api.component.DynamicApiLoader;
import com.manager.web.app.api.repository.DevhubApiMappingRepository;
import com.manager.web.app.api.vo.DevhubApiMapping;
import com.manager.web.app.plugins.OpenApiBuilder;

import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v4")
public class ApiDoccument {

	@Autowired
	private DynamicApiLoader dynamicApiLoader;

	@Autowired
	private DevhubApiMappingRepository devhubApiMappingRepository;

	@GetMapping("/api-docs")
	public String listDynamicApis() {
		Paths paths = dynamicApiLoader.getOpenAPI().getPaths();
		Map<String, Map<String, Operation>> apiDetails = new HashMap<>();

		for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
			String path = entry.getKey();
			PathItem pathItem = entry.getValue();
			Map<String, Operation> methods = new HashMap<>();

			if (pathItem.getGet() != null) {
				methods.put("GET", pathItem.getGet());
			}
			if (pathItem.getPost() != null) {
				methods.put("POST", pathItem.getPost());
			}
			if (pathItem.getPut() != null) {
				methods.put("PUT", pathItem.getPut());
			}
			if (pathItem.getDelete() != null) {
				methods.put("DELETE", pathItem.getDelete());
			}
			apiDetails.put(path, methods);

		}

		new OpenApiBuilder();
		return "{\r\n"
				+ "  \"openapi\": \"3.0.0\",\r\n"
				+ "  \"info\": {\r\n"
				+ "    \"title\": \"My API\",\r\n"
				+ "    \"version\": \"1.0.0\"\r\n"
				+ "  },\r\n"
				+ "  \"paths\": {\r\n"
				+ "    \"/api/users/{id}\": {\r\n"
				+ "      \"get\": {\r\n"
				+ "        \"summary\": \"API endpoint\",\r\n"
				+ "        \"parameters\": [\r\n"
				+ "          {\r\n"
				+ "            \"name\": \"id\",\r\n"
				+ "            \"in\": \"path\",\r\n"
				+ "            \"required\": true,\r\n"
				+ "            \"schema\": {\r\n"
				+ "              \"type\": \"integer\"\r\n"
				+ "            }\r\n"
				+ "          }\r\n"
				+ "        ]\r\n"
				+ "      }\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}";
	}
}
