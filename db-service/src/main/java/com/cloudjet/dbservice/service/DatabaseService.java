package com.cloudjet.dbservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.dto.CreateDatabaseRequest;
import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;

@Service
public class DatabaseService {
    private final DatabaseRepository repository;

    public DatabaseService(DatabaseRepository repository) {
        this.repository = repository;
    }

    public String create(CreateDatabaseRequest request){
        DatabaseInstance db = new DatabaseInstance();

        db.setDbName(request.getDbName());
        db.setOwnerEmail(request.getOwnerEmail());
        db.setRegion(request.getRegion());
        db.setPlan(request.getPlan());
        db.setStatus("CREATING");

        repository.save(db);

        return "Database provisioning started";
    }

    public List<DatabaseInstance> getAll(){
        return repository.findAll();
    }
    
}
