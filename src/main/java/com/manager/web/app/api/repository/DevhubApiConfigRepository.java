package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiConfigRepository extends JpaRepository<DevhubApiConfig, Serializable> {
    // Custom query methods if needed
}
