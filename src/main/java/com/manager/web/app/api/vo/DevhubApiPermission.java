package com.manager.web.app.api.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_permissions")
public class DevhubApiPermission {

	@Id
	@Column(name = "PERMISSION_ID", nullable = false, length = 255)
	private String permissionId;

	@Column(name = "PERMISSION_NAME", nullable = false, length = 255)
	private String permissionName;

	@Column(name = "PERMISSION_DESCRIPTION")
	private String permissionDescription;

	// Getters and Setters
}
