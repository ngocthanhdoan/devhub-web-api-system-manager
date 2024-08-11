package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiDataBase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiDataBaseRepository extends JpaRepository<DevhubApiDataBase, Serializable> {
    // Custom query methods if needed
}
