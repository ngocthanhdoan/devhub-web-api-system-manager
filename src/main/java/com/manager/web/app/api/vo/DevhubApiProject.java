package com.manager.web.app.api.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_projects")
public class DevhubApiProject {

	@Id
	@Column(name = "PROJECT_ID", nullable = false, length = 255)
	private String projectId;

	@Column(name = "PROJECT_NAME", nullable = false, length = 255)
	private String projectName;

	@Column(name = "PROJECT_DESCRIPTION")
	private String projectDescription;

	@Column(name = "PROJECT_CREATED_AT")
	private LocalDateTime projectCreatedAt;

	@Column(name = "PROJECT_UPDATED_AT")
	private LocalDateTime projectUpdatedAt;

	// Getters and Setters
}
