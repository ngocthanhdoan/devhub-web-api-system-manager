package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiUserPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiUserPermissionRepository extends JpaRepository<DevhubApiUserPermission, Serializable> {
    // Custom query methods if needed
}
