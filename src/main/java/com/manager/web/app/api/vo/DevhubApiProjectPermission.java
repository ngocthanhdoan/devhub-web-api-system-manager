package com.manager.web.app.api.vo;

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
@Table(name = "devhub_api_project_permissions")
public class DevhubApiProjectPermission {

	@Id
	@Column(name = "PROJECT_PERMISSION_ID", nullable = false, length = 255)
	private String projectPermissionId;

	@Column(name = "PROJECT_ID", nullable = false, length = 255)
	private String projectId;

	@Column(name = "USER_ID", nullable = false, length = 255)
	private String userId;

	@Column(name = "PERMISSION_ID", nullable = false, length = 255)
	private String permissionId;

	// Getters and Setters
}
