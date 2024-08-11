package com.manager.web.app.api.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "devhub_api_databases")
public class DevhubApiDataBase {

	@Id
	@Column(name = "id", nullable = false, length = 255)
	private String id; // Sử dụng String thay vì Integer vì bạn không dùng khóa tự tăng

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "host", nullable = false, length = 255)
	private String host;

	@Column(name = "port", nullable = false)
	private Integer port;

	@Column(name = "username", nullable = false, length = 255)
	private String username;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

}
