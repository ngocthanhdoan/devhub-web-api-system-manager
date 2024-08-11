package com.manager.web.app.api.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "devhub_api_cross")
public class DevhubApiCross {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uri_api", nullable = false, length = 255)
    private String uriApi;

    @Column(name = "allowed_origins")
    private String allowedOrigins;  // Lưu danh sách các origins được phép

    @Column(name = "allowed_methods", length = 255)
    private String allowedMethods;

    @Column(name = "allowed_headers", length = 255)
    private String allowedHeaders;

    @Column(name = "exposed_headers", length = 255)
    private String exposedHeaders;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "allow_credentials")
    private Boolean allowCredentials = false;

	public synchronized Integer getId() {
		return id;
	}

	public synchronized void setId(Integer id) {
		this.id = id;
	}

	public synchronized String getUriApi() {
		return uriApi;
	}

	public synchronized void setUriApi(String uriApi) {
		this.uriApi = uriApi;
	}

	public synchronized String getAllowedOrigins() {
		return allowedOrigins;
	}

	public synchronized void setAllowedOrigins(String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	public synchronized String getAllowedMethods() {
		return allowedMethods;
	}

	public synchronized void setAllowedMethods(String allowedMethods) {
		this.allowedMethods = allowedMethods;
	}

	public synchronized String getAllowedHeaders() {
		return allowedHeaders;
	}

	public synchronized void setAllowedHeaders(String allowedHeaders) {
		this.allowedHeaders = allowedHeaders;
	}

	public synchronized String getExposedHeaders() {
		return exposedHeaders;
	}

	public synchronized void setExposedHeaders(String exposedHeaders) {
		this.exposedHeaders = exposedHeaders;
	}

	public synchronized Integer getMaxAge() {
		return maxAge;
	}

	public synchronized void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public synchronized Boolean getAllowCredentials() {
		return allowCredentials;
	}

	public synchronized void setAllowCredentials(Boolean allowCredentials) {
		this.allowCredentials = allowCredentials;
	}

}
