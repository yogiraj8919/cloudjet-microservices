package com.cloudjet.dbservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.cloudjet.dbservice.config.RabbitMQConfig;
import com.cloudjet.dbservice.dto.DeleteRequest;

@Service
public class DeletePublisher {

    private final RabbitTemplate rabbitTemplate;

    public DeletePublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishDeleteRequest(Long dbId){
        DeleteRequest deleteRequest = new DeleteRequest(dbId);

        rabbitTemplate.convertAndSend(RabbitMQConfig.DELETE_QUEUE,deleteRequest);
    }
    
}
