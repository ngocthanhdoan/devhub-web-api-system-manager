package com.manager.web.app.api.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_permission_mapping")
public class DevhubApiPermissionMapping {

	@Id
	@Column(name = "MAPPING_ID", nullable = false, length = 255)
	private String mappingId;

	@Column(name = "CONTROLLER_NAME", nullable = false, length = 255)
	private String controllerName;

	@Column(name = "PERMISSION_ID", nullable = false, length = 255)
	private String permissionId;

	// Getters and Setters
	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
}
