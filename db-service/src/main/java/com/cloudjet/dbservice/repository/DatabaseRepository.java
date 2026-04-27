package com.cloudjet.dbservice.repository;

import com.cloudjet.dbservice.entity.DatabaseInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseRepository extends JpaRepository<DatabaseInstance, Long> {
}
