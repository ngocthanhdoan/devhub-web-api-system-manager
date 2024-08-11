package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiHost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiHostRepository extends JpaRepository<DevhubApiHost, Serializable> {
    // Custom query methods if needed
}
