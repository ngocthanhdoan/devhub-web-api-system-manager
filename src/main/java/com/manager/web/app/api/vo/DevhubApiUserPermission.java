package com.manager.web.app.api.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_user_permissions")
public class DevhubApiUserPermission {

	@Id
	@Column(name = "USER_PERMISSION_ID", nullable = false, length = 255)
	private String userPermissionId;

	@Column(name = "USER_ID", nullable = false, length = 255)
	private String userId;

	@Column(name = "PERMISSION_ID", nullable = false, length = 255)
	private String permissionId;

	// Getters and Setters
}
