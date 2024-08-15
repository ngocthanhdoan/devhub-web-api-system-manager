package com.manager.web.app.api.vo;

import java.time.LocalDateTime;

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
@Table(name = "devhub_api_hosts")
public class DevhubApiHost {

	@Id
	@Column(name = "HOST_ID", nullable = false, length = 255)
	private String hostId;

	@Column(name = "HOST_NAME", nullable = false, length = 255)
	private String hostName;

	@Column(name = "HOST_IP_ADDRESS", nullable = false, length = 255)
	private String hostIpAddress;

	@Column(name = "HOST_CREATED_AT")
	private LocalDateTime hostCreatedAt;

	@Column(name = "HOST_UPDATED_AT")
	private LocalDateTime hostUpdatedAt;

	// Getters and Setters
}
