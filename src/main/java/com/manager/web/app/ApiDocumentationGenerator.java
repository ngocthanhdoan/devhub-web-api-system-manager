package com.manager.web.app;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.FileWriter;
import java.io.IOException;

public class ApiDocumentationGenerator {

    public static void main(String[] args) throws IOException {
        List<Map<String, String>> apiDefinition = List.of(
            Map.of(
                "endpoint", "/users",
                "method", "GET",
                "description", "Retrieve all users",
                "parameters", "None"
            ),
            Map.of(
                "endpoint", "/users/{id}",
                "method", "GET",
                "description", "Retrieve user by ID",
                "parameters", "id: integer"
            )
        );

        generateOpenApiDocumentation(apiDefinition, "openapi.json");
    }

    public static void generateOpenApiDocumentation(List<Map<String, String>> apiDefinition, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode openApi = mapper.createObjectNode();
        openApi.put("openapi", "3.0.0");
        ObjectNode info = openApi.putObject("info");
        info.put("title", "API Documentation");
        info.put("version", "1.0.0");
        ObjectNode paths = openApi.putObject("paths");

        for (Map<String, String> api : apiDefinition) {
            String endpoint = api.get("endpoint");
            String method = api.get("method").toLowerCase();
            ObjectNode pathItem = paths.putObject(endpoint);
            ObjectNode operation = pathItem.putObject(method);
            operation.put("summary", api.get("description"));
            ObjectNode responses = operation.putObject("responses");
            responses.putObject("200").put("description", "Successful operation");
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(openApi));
        }
    }
}
