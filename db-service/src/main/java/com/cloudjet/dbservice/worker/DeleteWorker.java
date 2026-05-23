package com.cloudjet.dbservice.worker;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cloudjet.dbservice.config.RabbitMQConfig;
import com.cloudjet.dbservice.dto.DeleteRequest;
import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.repository.DatabaseRepository;
import com.cloudjet.dbservice.service.provisioner.DatabaseProvisioner;
import com.cloudjet.dbservice.service.provisioner.ProvisionerFactory;

@Component
public class DeleteWorker {
    private static final Logger log =
            LoggerFactory.getLogger(DeleteWorker.class);
    
    private final DatabaseRepository repository;

    private final ProvisionerFactory provisionerFactory;

    public DeleteWorker(
            DatabaseRepository repository,
            ProvisionerFactory provisionerFactory
    ) {
        this.repository = repository;
        this.provisionerFactory = provisionerFactory;
    }

    @RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
    public void processDeleteRequest(DeleteRequest request){
        Long dbId = request.getDbId();

        DatabaseInstance db =
                repository.findById(dbId)
                        .orElseThrow(() ->
                                new RuntimeException("DB not found"));
        
        try{
            DatabaseProvisioner provisioner = provisionerFactory.getProvisioner(db.getEngineType());
            provisioner.delete(db);
            repository.delete(db);

            log.info("Database {} deleted successfully",dbId);


        }catch (Exception e){
            db.setStatus("DELETE_FAILED");
            repository.save(db);

            log.error("Delete failed for DB {} : {}",
                    dbId,
                    e.getMessage()
            );
        }                         

    }
}
