package com.manager.web.app.api.trx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manager.web.app.api.component.DynamicApiLoader;
import com.manager.web.app.api.repository.DevhubApiMappingRepository;
import com.manager.web.app.api.vo.DevhubApiMapping;

import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.PathItem;
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
    public List<DevhubApiMapping> listDynamicApis() {
        // Paths paths = dynamicApiLoader.getOpenAPI().getPaths();
        // Map<String, Map<String, Operation>> apiDetails = new HashMap<>();

        // for (Map.Entry<String, PathItem> entry : paths.entrySet()) {
        //     String path = entry.getKey();
        //     PathItem pathItem = entry.getValue();
        //     Map<String, Operation> methods = new HashMap<>();

        //     if (pathItem.getGet() != null) {
        //         methods.put("GET", pathItem.getGet());
        //     }
        //     if (pathItem.getPost() != null) {
        //         methods.put("POST", pathItem.getPost());
        //     }
        //     if (pathItem.getPut() != null) {
        //         methods.put("PUT", pathItem.getPut());
        //     }
        //     if (pathItem.getDelete() != null) {
        //         methods.put("DELETE", pathItem.getDelete());
        //     }

        //     apiDetails.put(path, methods);
        // }

        // return apiDetails;
          return devhubApiMappingRepository.findAll();
    }
}
