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
@Table(name = "devhub_api_system_configurations")
public class DevhubApiSystemConfiguration {

	@Id
	@Column(name = "CONFIGURATION_ID", nullable = false, length = 255)
	private String configurationId;

	@Column(name = "CONFIGURATION_NAME", nullable = false, length = 255)
	private String configurationName;

	@Column(name = "CONFIGURATION_VALUE", nullable = false)
	private String configurationValue;

	@Column(name = "CONFIGURATION_CREATED_AT")
	private LocalDateTime configurationCreatedAt;

	@Column(name = "CONFIGURATION_UPDATED_AT")
	private LocalDateTime configurationUpdatedAt;

	// Getters and Setters

}
