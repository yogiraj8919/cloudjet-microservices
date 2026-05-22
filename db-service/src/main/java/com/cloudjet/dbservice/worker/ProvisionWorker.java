package com.cloudjet.dbservice.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cloudjet.dbservice.config.RabbitMQConfig;
import com.cloudjet.dbservice.dto.ProvisionRequest;
import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;
import com.cloudjet.dbservice.service.provisioner.DatabaseProvisioner;
import com.cloudjet.dbservice.service.provisioner.ProvisionerFactory;


@Component
public class ProvisionWorker {
    private static final Logger log = LoggerFactory.getLogger(ProvisionWorker.class);

    private final DatabaseRepository databaseRepository;

    private final ProvisionerFactory provisionerFactory;

    public ProvisionWorker(DatabaseRepository databaseRepository, ProvisionerFactory provisionerFactory){
        this.databaseRepository = databaseRepository;
        this.provisionerFactory = provisionerFactory;
    }

    @RabbitListener(queues = RabbitMQConfig.PROVISION_QUEUE)
    public void processProvisionRequest(ProvisionRequest request){
         Long dbId = request.getDbId();

    log.info(

            "Provision worker received DB {}",

            dbId

    );

    DatabaseInstance db =
            databaseRepository.findById(dbId)
                    .orElseThrow(() ->
                            new RuntimeException("DB not found"));
    try {
        DatabaseProvisioner provisioner =
                provisionerFactory.getProvisioner(
                        db.getEngineType()
                );
        provisioner.provision(db);
        log.info(
                "Provisioning completed for DB {}",
                dbId
        );
    } catch (Exception e){
        log.error(
                "Provisioning failed for DB {} : {}",
                dbId,
                e.getMessage()
        );

        db.setStatus("FAILED");
        databaseRepository.save(db);
    }
    }
}
