package com.manager.web.app.api.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devhub_api_info")
public class DevhubApiInfo {

    @Id
    @Column(name = "uri_api", length = 255)
    private String uriApi;

    @Column(name = "title_api", length = 255)
    private String titleApi;

    @Column(name = "version", length = 50)
    private String version;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "path", length = 255)
    private String path;

    @Column(name = "http_method", length = 50)
    private String httpMethod;

    @Column(name = "request_body", length = 1000)
    private String requestBody;

    @Column(name = "parameters", length = 1000)
    private String parameters;

    @Column(name = "responses", length = 1000)
    private String responses;

    @Column(name = "schemas", length = 1000)
    private String schemas;

    @Column(name = "tags", length = 500)
    private String tags;

    @Column(name = "security_requirements", length = 500)
    private String securityRequirements;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}