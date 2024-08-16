package com.manager.web.app.plugins;

import java.util.Map;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

public class OpenApiBuilder {
    public static OpenAPI buildOpenApi() {
        return new OpenAPI()
                .info(buildInfo())
                .servers(buildServers())
                .tags(buildTags())
                .components(buildComponents())
                .security(buildSecurityRequirements());
    }

    private static Info buildInfo() {
        return new Info()
                .title("My API")
                .version("1.0.0")
                .description("This is my API");
    }

    private static java.util.List<Server> buildServers() {
        return java.util.Arrays.asList(
                new Server().url("https://api.example.com/v1")
        );
    }

    private static java.util.List<Tag> buildTags() {
        return java.util.Arrays.asList(
                new Tag().name("Category"),
                new Tag().name("Tag")
        );
    }

    private static Components buildComponents() {
        return new Components()
                .schemas(buildSchemas())
                .parameters(buildParameters())
                .responses(buildResponses())
                .securitySchemes(buildSecuritySchemes());
    }

    private static Map<String, Schema> buildSchemas() {
        Map<String, Schema> schemas = new java.util.HashMap<>();
        schemas.put("GeneralError", new io.swagger.v3.oas.models.media.Schema<>()
                .type("object")
                .addProperties("code", new io.swagger.v3.oas.models.media.IntegerSchema().format("int32"))
                .addProperties("message", new io.swagger.v3.oas.models.media.StringSchema()));
        schemas.put("Category", new io.swagger.v3.oas.models.media.Schema<>()
                .type("object")
                .addProperties("id", new io.swagger.v3.oas.models.media.IntegerSchema().format("int64"))
                .addProperties("name", new io.swagger.v3.oas.models.media.StringSchema()));
        schemas.put("Tag", new io.swagger.v3.oas.models.media.Schema<>()
                .type("object")
                .addProperties("id", new io.swagger.v3.oas.models.media.IntegerSchema().format("int64"))
                .addProperties("name", new io.swagger.v3.oas.models.media.StringSchema()));
        return schemas;
    }

    private static java.util.Map<String, Parameter> buildParameters() {
        java.util.Map<String, Parameter> parameters = new java.util.HashMap<>();
        parameters.put("skipParam", new Parameter()
                .name("skip")
                .in("query")
                .description("number of items to skip")
                .required(true)
                .schema(new io.swagger.v3.oas.models.media.IntegerSchema().format("int32")));
        parameters.put("limitParam", new Parameter()
                .name("limit")
                .in("query")
                .description("max records to return")
                .required(true)
                .schema(new io.swagger.v3.oas.models.media.IntegerSchema().format("int32")));
        return parameters;
    }

    private static ApiResponses buildResponses() {
        return new ApiResponses()
                .addApiResponse("NotFound", new ApiResponse().description("Entity not found."))
                .addApiResponse("IllegalInput", new ApiResponse().description("Illegal input for operation."))
                .addApiResponse("GeneralError", new ApiResponse()
                        .description("General Error")
                        .content(new io.swagger.v3.oas.models.media.Content()
                                .addMediaType("application/json", new io.swagger.v3.oas.models.media.MediaType()
                                        .schema(new io.swagger.v3.oas.models.media.Schema<>().$ref("#/components/schemas/GeneralError")))));
    }

    private static java.util.Map<String, SecurityScheme> buildSecuritySchemes() {
        java.util.Map<String, SecurityScheme> securitySchemes = new java.util.HashMap<>();
        securitySchemes.put("api_key", new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("api_key"));
        securitySchemes.put("petstore_auth", new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new io.swagger.v3.oas.models.security.OAuthFlows()
                        .implicit(new io.swagger.v3.oas.models.security.OAuthFlow()
                                .authorizationUrl("https://example.org/api/oauth/dialog")
                                .scopes(new io.swagger.v3.oas.models.security.Scopes()
                                        .addString("write:pets", "modify pets in your account")
                                        .addString("read:pets", "read your pets")))));
        return securitySchemes;
    }

    private static java.util.List<SecurityRequirement> buildSecurityRequirements() {
        java.util.List<SecurityRequirement> securityRequirements = new java.util.ArrayList<>();
        securityRequirements.add(new SecurityRequirement().addList("api_key"));
        securityRequirements.add(new SecurityRequirement().addList("petstore_auth", "read:pets"));
        return securityRequirements;
    }
}