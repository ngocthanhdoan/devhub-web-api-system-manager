package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiCross;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiCrossRepository extends JpaRepository<DevhubApiCross, Serializable> {
    // Custom query methods if needed
}
