package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiPermissionRepository extends JpaRepository<DevhubApiPermission, Serializable> {
    // Custom query methods if needed
}
