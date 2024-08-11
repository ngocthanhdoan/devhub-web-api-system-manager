package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiMappingRepository extends JpaRepository<DevhubApiMapping, Serializable> {
    // Custom query methods if needed
}
