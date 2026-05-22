package com.cloudjet.dbservice.service.provisioner;

import java.util.UUID;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;

@Service
public class PostgresProvisioner implements DatabaseProvisioner{
    private static final Logger log =
            LoggerFactory.getLogger(PostgresProvisioner.class);

    private final DatabaseRepository repository;

    public PostgresProvisioner(DatabaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public void provision(DatabaseInstance db) {
        try {
            String containerName = db.getServerName();
            int port = generatePort();

            

             String command = String.format(
                    "docker run -d " +
                            "--name %s " +
                            "-e POSTGRES_USER=%s " +
                            "-e POSTGRES_PASSWORD=%s " +
                            "-e POSTGRES_DB=%s " +
                            "-p %d:5432 postgres:15",
                    containerName,
                    db.getUsername(),
                    db.getPassword(),
                    db.getDbName(),
                    port
            );

            log.info("Executing command: {}", command);

            ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", command);
            Process process = pb.start();

            String output = new String(process.getInputStream().readAllBytes());

            String error = new String(process.getErrorStream().readAllBytes());

            int exitCode = process.waitFor();

            log.info("Docker Output: {}", output);
            log.error("Docker Error: {}", error);

            if(exitCode != 0){
                    throw new RuntimeException("Container creation failed: " + error);
            }

            String containerId = output.trim();

            db.setContainerId(containerId);
            db.setPort(port);
            
            db.setEndpoint("localhost:" + port);
            db.setStatus("AVAILABLE");
            db.setResourceId(UUID.randomUUID().toString()

);

            repository.save(db);

            log.info("Provisioned PostgreSQL container {}", containerId);

        } catch (IOException e) {
            log.error("Provisioning failed: {}", e.getMessage());

            db.setStatus("FAILED");

            repository.save(db);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Provisioning interrupted: {}", e.getMessage());

            db.setStatus("FAILED");

            repository.save(db);
        }
    }
    @Override
    public void delete(DatabaseInstance db) {
        // later
    }

    private int generatePort() {
        return (int) (10000 + Math.random() * 5000);
    }
    
}
