package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiProjectPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiProjectPermissionRepository extends JpaRepository<DevhubApiProjectPermission, Serializable> {
    // Custom query methods if needed
}
