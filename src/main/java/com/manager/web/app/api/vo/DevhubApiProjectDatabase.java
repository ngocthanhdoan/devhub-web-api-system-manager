package com.manager.web.app.api.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_project_databases")
public class DevhubApiProjectDatabase {

	@Id
	@Column(name = "PROJECT_DATABASE_ID", nullable = false, length = 255)
	private String projectDatabaseId;

	@Column(name = "PROJECT_ID", nullable = false, length = 255)
	private String projectId;

	@Column(name = "DATABASE_ID", nullable = false, length = 255)
	private String databaseId;

	// Getters and Setters
}
