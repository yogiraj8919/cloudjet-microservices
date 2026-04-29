package com.cloudjet.dbservice.repository;

import com.cloudjet.dbservice.entity.DatabaseInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface DatabaseRepository extends JpaRepository<DatabaseInstance, Long> {
    List<DatabaseInstance> findByStatus(String status);
}
