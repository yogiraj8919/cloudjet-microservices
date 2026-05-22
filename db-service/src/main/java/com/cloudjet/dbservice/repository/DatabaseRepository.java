package com.cloudjet.dbservice.repository;

import com.cloudjet.dbservice.entity.DatabaseInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface DatabaseRepository extends JpaRepository<DatabaseInstance, Long> {
    List<DatabaseInstance> findByStatus(String status);
    List<DatabaseInstance> findByOwnerEmail(String ownerEmail);
    Optional<DatabaseInstance> findByIdAndOwnerEmail(Long id, String email);
    boolean existsByServerName(String serverName);
}
