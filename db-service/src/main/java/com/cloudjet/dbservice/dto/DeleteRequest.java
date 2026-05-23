package com.cloudjet.dbservice.dto;

import java.io.Serializable;

public class DeleteRequest implements Serializable {

    private Long dbId;

    public DeleteRequest() {
    }

    public DeleteRequest(Long dbId) {
        this.dbId = dbId;
    }

    public Long getDbId() {
        return dbId;
    }

    public DeleteRequest setDbId(Long dbId) {
        this.dbId = dbId;
        return this;
    }
}
