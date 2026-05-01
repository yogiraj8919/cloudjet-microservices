package com.cloudjet.dbservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;

@Service
public class ProvisioningService {

    private static Logger log = LoggerFactory.getLogger(ProvisioningService.class);

    private final DatabaseRepository repository;

    public ProvisioningService(DatabaseRepository repository){
        this.repository = repository;
    }

    public void provisionDatabase(Long dbId){
        log.info("Started provisioning for DB {}", dbId);
        try {
            Thread.sleep(5000); // simulate delay

            DatabaseInstance db = repository.findById(dbId)
                    .orElseThrow(() -> new RuntimeException("DB not found"));

            db.setStatus("AVAILABLE");
            repository.save(db);

            log.info("Provisioning completed for DB {}", dbId);

        } catch (Exception e) {

            log.error("Provisioning failed for DB {}", dbId);

            DatabaseInstance db = repository.findById(dbId).orElse(null);
            if (db != null) {
                db.setStatus("FAILED");
                repository.save(db);
            }
        }

    }
    
}
