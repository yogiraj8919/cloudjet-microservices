package com.cloudjet.dbservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudjet.dbservice.dto.CreateDatabaseRequest;
import com.cloudjet.dbservice.entity.DatabaseInstance;
import com.cloudjet.dbservice.service.DatabaseService;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {
    private final DatabaseService service;

    public DatabaseController(DatabaseService service){
        this.service = service;
    }

    @PostMapping("/create")
    public String create(@RequestBody CreateDatabaseRequest request){
        return service.create(request);
    }

    @GetMapping("/all")
    public List<DatabaseInstance> getAll(){
        return service.getAll();
    }
    
}
