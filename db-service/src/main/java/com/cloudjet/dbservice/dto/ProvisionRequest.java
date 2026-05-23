package com.cloudjet.dbservice.dto;


public class ProvisionRequest{
    private Long dbId;

    public ProvisionRequest() {
    }

    public ProvisionRequest(Long dbId) {
        this.dbId = dbId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }
}
