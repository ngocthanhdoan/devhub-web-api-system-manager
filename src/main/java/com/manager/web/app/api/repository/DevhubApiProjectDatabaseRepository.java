package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiProjectDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiProjectDatabaseRepository extends JpaRepository<DevhubApiProjectDatabase, Serializable> {
    // Custom query methods if needed
}
