package com.cloudjet.dbservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.dto.CreateDatabaseRequest;
import com.cloudjet.dbservice.dto.DatabaseResponse;
import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;

@Service
public class DatabaseService {
    private final DatabaseRepository repository;

    public DatabaseService(DatabaseRepository repository) {
        this.repository = repository;
    }

    public String create(CreateDatabaseRequest request, String email){
        DatabaseInstance db = new DatabaseInstance();

        db.setDbName(request.getDbName());
        db.setOwnerEmail(email);
        db.setRegion(request.getRegion());
        db.setPlan(request.getPlan());
        db.setStatus("CREATING");
        db.setCreatedAt(LocalDateTime.now());

        repository.save(db);

        return "Database provisioning started";
    }

    public List<DatabaseInstance> getUserDatabases(String email,String role) {

        if("ADMIN".equals(role)){
            return repository.findAll();
        }
        
        return repository.findByOwnerEmail(email);
    }

    public DatabaseResponse getById(Long id) {

        DatabaseInstance db = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DB not found"));

        return mapToResponse(db);
    }

    private DatabaseResponse mapToResponse(DatabaseInstance db){
        String endpoint = db.getDbName() + ".cloudjet.local";
        int port = 5432;

        return new DatabaseResponse(db.getId(),db.getDbName(),db.getOwnerEmail(),db.getRegion(),db.getPlan(),db.getStatus(),db.getCreatedAt(),endpoint,port);
    }
    
}
