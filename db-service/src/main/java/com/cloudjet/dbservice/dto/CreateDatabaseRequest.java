package com.cloudjet.dbservice.dto;

import jakarta.persistence.Column;

public class CreateDatabaseRequest {


   
    private String dbName;
    private String region;
    private String plan;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
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
}