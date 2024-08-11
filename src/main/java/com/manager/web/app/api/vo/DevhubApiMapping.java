package com.manager.web.app.api.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_mapping")
public class DevhubApiMapping {

	@Id
	@Column(name = "uri_api", nullable = false, length = 255)
	private String uriApi;

	@Column(name = "class_name", nullable = false, length = 255)
	private String className;

	@Column(name = "method_api", nullable = false, length = 255)
	private String methodApi;

	@Column(name = "code_map_id", nullable = false, length = 50)
	private String code_map_id;
	@Column(name = "call_type", nullable = false, length = 50)
	private String callType;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public String getUriApi() {
		return uriApi;
	}

	public void setUriApi(String uriApi) {
		this.uriApi = uriApi;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodApi() {
		return methodApi;
	}

	public void setMethodApi(String methodApi) {
		this.methodApi = methodApi;
	}

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getCode_map_id() {
		return code_map_id;
	}

	public void setCode_map_id(String code_map_id) {
		this.code_map_id = code_map_id;
	}

}
