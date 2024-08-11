package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiPermissionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiPermissionMappingRepository extends JpaRepository<DevhubApiPermissionMapping, Serializable> {
    // Custom query methods if needed
}
