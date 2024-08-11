package com.manager.web.app.api.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "devhub_api_code_map")
public class DevhubApiCodeMap {
    @Id
    @Column(name = "code_map_id", nullable = false, length = 255)
    private String codeMapId;

    @Column(name = "code_map_sql", nullable = false, columnDefinition = "TEXT")
    private String codeMapSql;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

	public String getCodeMapId() {
		return codeMapId;
	}

	public void setCodeMapId(String codeMapId) {
		this.codeMapId = codeMapId;
	}

	public String getCodeMapSql() {
		return codeMapSql;
	}

	public void setCodeMapSql(String codeMapSql) {
		this.codeMapSql = codeMapSql;
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
}
