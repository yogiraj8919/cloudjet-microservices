package com.cloudjet.dbservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.config.RabbitMQConfig;
import com.cloudjet.dbservice.dto.ProvisionRequest;

@Service
public class ProvisionPublisher {
    private static final Logger log =
            LoggerFactory.getLogger(ProvisionPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public ProvisionPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProvisionRequest(Long dbId){
        ProvisionRequest request = new ProvisionRequest(dbId);

        rabbitTemplate.convertAndSend(RabbitMQConfig.PROVISION_QUEUE,request);
        log.info("Provision event published for DB {}", dbId);
    }

}
