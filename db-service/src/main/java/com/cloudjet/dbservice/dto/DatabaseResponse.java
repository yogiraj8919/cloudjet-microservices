package com.cloudjet.dbservice.dto;

import java.time.LocalDateTime;

public class DatabaseResponse {

    private Long id;

    private String serverName;

    private String dbName;

    private String ownerEmail;

    private String region;

    private String plan;

    private String status;

    private String engineVersion;

    private LocalDateTime createdAt;

    private String endpoint;

    private Integer port;

    private String username;

    public DatabaseResponse(
            Long id,
            String serverName,
            String dbName,
            String ownerEmail,
            String region,
            String plan,
            String status,
            String engineVersion,
            LocalDateTime createdAt,
            String endpoint,
            Integer port,
            String username
    ) {

        this.id = id;
        this.serverName = serverName;
        this.dbName = dbName;
        this.ownerEmail = ownerEmail;
        this.region = region;
        this.plan = plan;
        this.status = status;
        this.engineVersion = engineVersion;
        this.createdAt = createdAt;
        this.endpoint = endpoint;
        this.port = port;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public String getDbName() {
        return dbName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getRegion() {
        return region;
    }

    public String getPlan() {
        return plan;
    }

    public String getStatus() {
        return status;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }
}