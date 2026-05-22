package com.cloudjet.dbservice.service.provisioner;

import org.springframework.stereotype.Service;

@Service
public class ProvisionerFactory {
    private final PostgresProvisioner postgresProvisioner;

    public ProvisionerFactory(PostgresProvisioner postgresProvisioner) {
        this.postgresProvisioner = postgresProvisioner;
    }

    public DatabaseProvisioner getProvisioner(String engineType) {

        if ("POSTGRES".equalsIgnoreCase(engineType)) {
            return postgresProvisioner;
        }

        throw new RuntimeException("Unsupported engine type");
    }
}
