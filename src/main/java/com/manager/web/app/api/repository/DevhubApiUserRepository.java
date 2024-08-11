package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiUserRepository extends JpaRepository<DevhubApiUser, Serializable> {
    // Custom query methods if needed
}
