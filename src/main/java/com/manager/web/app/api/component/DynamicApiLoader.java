package com.manager.web.app.api.component;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.manager.web.app.api.repository.DevhubApiMappingRepository;
import com.manager.web.app.api.vo.DevhubApiMapping;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.parameters.Parameter;
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
            System.out.println(apis);
            Operation operation = new Operation()
                    .summary(api.getClassName() + " API")
                    .addParametersItem(new Parameter().name("param").description("A sample parameter"))
                    .responses(new ApiResponses().addApiResponse("200", new ApiResponse().description("Successful operation")));

            PathItem pathItem = new PathItem();

            // Chọn phương thức dựa trên methodApi từ cơ sở dữ liệu
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
                default: // Mặc định là GET
                    pathItem.get(operation);
                    break;
            }

            paths.addPathItem(api.getUriApi(), pathItem);
        }

        if (openAPI != null) {
            openAPI.setPaths(paths);
        }
    }

    public OpenAPI getOpenAPI() {
        init();
        return openAPI;
    }
}
