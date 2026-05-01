package com.cloudjet.dbservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.dto.CreateDatabaseRequest;
import com.cloudjet.dbservice.dto.DatabaseResponse;
import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;

@Service
public class DatabaseService {
    private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);

    private final DatabaseRepository repository;
    private final ProvisioningService provisioningService;

    public DatabaseService(DatabaseRepository repository,ProvisioningService service) {
        this.repository = repository;
        this.provisioningService = service;
    }

    public DatabaseResponse create(CreateDatabaseRequest request, String email){

        log.info("User {} requested DB creation: {}", email, request.getDbName());

        DatabaseInstance db = new DatabaseInstance();

        db.setDbName(request.getDbName());
        db.setOwnerEmail(email);
        db.setRegion(request.getRegion());
        db.setPlan(request.getPlan());
        db.setStatus("CREATING");
        db.setCreatedAt(LocalDateTime.now());

        DatabaseInstance savedDb = repository.save(db);

        provisioningService.provisionDatabase(savedDb.getId());

        log.info("DB {} created with status CREATING", savedDb.getId());

        return mapToResponse(savedDb);
    }

    public List<DatabaseResponse> getUserDatabases(String email,String role) {
        log.info("Fetching databases for user {} with role {}", email, role);

        List<DatabaseInstance> dbs;

        if("ADMIN".equals(role)){
            dbs =  repository.findAll();
            log.info("Admin {} fetched all databases", email);
        }else{ 
            dbs = repository.findByOwnerEmail(email);
            log.info("User {} fetched {} databases", email, dbs.size());
        }

        return dbs.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DatabaseResponse getById(Long id, String email, String role) {

        log.info("User {} with role {} requested DB with id {}",email,role,id);

        DatabaseInstance db;

        if("ADMIN".equalsIgnoreCase(role)){
            db = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Database not found for id {}", id);
                        return new RuntimeException("Database not found");
                    });
        }else{
            db = repository.findByIdAndOwnerEmail(id, email)
                    .orElseThrow(() -> {
                        log.warn("Unauthorized access attempt by user {} for DB id {}", email, id);
                        return new RuntimeException("Database not found or access denied");
                    });
        }

        log.info("Database {} fetched successfully for user {}", id, email);

        return mapToResponse(db);
    }

    private DatabaseResponse mapToResponse(DatabaseInstance db){
        String endpoint = db.getDbName() + ".cloudjet.local";
        int port = 5432;

        return new DatabaseResponse(db.getId(),db.getDbName(),db.getOwnerEmail(),db.getRegion(),db.getPlan(),db.getStatus(),db.getCreatedAt(),endpoint,port);
    }
    
}
