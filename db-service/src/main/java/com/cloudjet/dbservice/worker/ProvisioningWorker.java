package com.cloudjet.dbservice.worker;

import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ProvisioningWorker {

    private final DatabaseRepository repository;

    public ProvisioningWorker(DatabaseRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void processProvisioning() {

        List<DatabaseInstance> creatingList =
                repository.findByStatus("CREATING");

        for (DatabaseInstance db : creatingList) {

            if (db.getCreatedAt() != null &&
                db.getCreatedAt().plusSeconds(10).isBefore(LocalDateTime.now())) {

                db.setStatus("RUNNING");
                repository.save(db);
}
        }
    }
}