package com.cloudjet.dbservice.service.provisioner;

import com.cloudjet.dbservice.entity.DatabaseInstance;

public interface DatabaseProvisioner {

    void provision(DatabaseInstance db);

    void delete(DatabaseInstance db);
}
