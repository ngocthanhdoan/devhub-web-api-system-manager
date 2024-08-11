package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiCodeMap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiCodeMapRepository extends JpaRepository<DevhubApiCodeMap, Serializable> {
    // Custom query methods if needed
}
