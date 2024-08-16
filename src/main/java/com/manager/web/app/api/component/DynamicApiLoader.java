package com.manager.web.app.api.component;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manager.web.app.api.repository.DevhubApiMappingRepository;
import com.manager.web.app.api.vo.DevhubApiMapping;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import jakarta.annotation.PostConstruct;

@Component
public class DynamicApiLoader {

    @Autowired
    private OpenAPI openAPI;

    @Autowired
    private DevhubApiMappingRepository devhubApiMappingRepository;

    @PostConstruct
    public void init() {
        List<DevhubApiMapping> apis = devhubApiMappingRepository.findAll();
        Paths paths = new Paths();

        for (DevhubApiMapping api : apis) {
            Operation operation = new Operation()
                    .summary(api.getClassName())
                   // .description(api.getDescription())
                    .tags(List.of(api.getClassName()))
                    .operationId(api.getClassName() + "Operation")
                    .responses(buildResponses());

            // Add request parameters based on api.getParams()
//            Map<String, String> params = api.getParams();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                Parameter parameter = new Parameter()
//                        .name(entry.getKey())
//                        .description(entry.getValue())
//                        .schema(new Schema<>().type("string"));
//                operation.addParametersItem(parameter);
//            }

            // Add virtual request parameters
            Parameter virtualParam = new Parameter()
                    .name("virtualParam")
                    .description("This is a virtual parameter")
                    .schema(new Schema<>().type("string"));
            operation.addParametersItem(virtualParam);

            // Add request body based on api.getRequestBody()
            if (null != null) {
                RequestBody requestBody = new RequestBody()
                        //.description(api.getRequestBody())
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().type("object"))));
                operation.requestBody(requestBody);
            } else {
                // Add virtual request body
                RequestBody virtualRequestBody = new RequestBody()
                        .description("This is a virtual request body")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().type("object"))));
                operation.requestBody(virtualRequestBody);
            }

            PathItem pathItem = new PathItem();

            // Choose the HTTP method based on api.getMethodApi()
            switch (api.getMethodApi().toUpperCase()) {
                case "POST":
                    pathItem.post(operation);
                    break;
                case "PUT":
                    pathItem.put(operation);
                    break;
                case "DELETE":
                    pathItem.delete(operation);
                    break;
                default: // Default to GET
                    pathItem.get(operation);
                    break;
            }

            paths.addPathItem(api.getUriApi(), pathItem);
        }

        if (openAPI != null) {
            openAPI.setPaths(paths);
        }
    }

    private ApiResponses buildResponses() {
        ApiResponses responses = new ApiResponses()
                .addApiResponse("200", new ApiResponse()
                        .description("Successful operation")
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new Schema<>().type("object")))));

        return responses;
    }

    public OpenAPI getOpenAPI() {
        init();
        return openAPI;
    }
}