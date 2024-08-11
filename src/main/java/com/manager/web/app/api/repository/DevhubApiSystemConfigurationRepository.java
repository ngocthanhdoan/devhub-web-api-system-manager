package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiSystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiSystemConfigurationRepository extends JpaRepository<DevhubApiSystemConfiguration, Serializable> {
    // Custom query methods if needed
}
