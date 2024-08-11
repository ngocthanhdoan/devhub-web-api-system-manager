package com.manager.web.app.api.repository;

import com.manager.web.app.api.vo.DevhubApiProject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;

public interface DevhubApiProjectRepository extends JpaRepository<DevhubApiProject, Serializable> {
    // Custom query methods if needed
}
