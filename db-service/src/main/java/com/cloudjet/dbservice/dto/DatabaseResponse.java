package com.cloudjet.dbservice.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

public class DatabaseResponse {

    private Long id;
    
    @Column(nullable = false,unique = true)
    private String dbName;
    private String ownerEmail;
    private String region;
    private String plan;
    private String status;
    private LocalDateTime createdAt;

    private String endpoint;
    private int port;

    public DatabaseResponse() {
    }

    public DatabaseResponse(Long id, String dbName, String ownerEmail,
                            String region, String plan, String status,
                            LocalDateTime createdAt,
                            String endpoint, int port) {

        this.id = id;
        this.dbName = dbName;
        this.ownerEmail = ownerEmail;
        this.region = region;
        this.plan = plan;
        this.status = status;
        this.createdAt = createdAt;
        this.endpoint = endpoint;
        this.port = port;
    }

    public Long getId() {
    return id;
}

        public void setId(Long id) {
            this.id = id;
        }

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getOwnerEmail() {
            return ownerEmail;
        }

        public void setOwnerEmail(String ownerEmail) {
            this.ownerEmail = ownerEmail;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
}
}