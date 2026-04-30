package com.cloudjet.dbservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudjet.dbservice.dto.CreateDatabaseRequest;
import com.cloudjet.dbservice.dto.DatabaseResponse;
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
    public ResponseEntity<?> create(@RequestBody CreateDatabaseRequest request,@RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(service.create(request, email));
       
    }

    @GetMapping("/{id}")
    public DatabaseResponse getById(@PathVariable Long id){
        return service.getById(id);
    }

    @GetMapping("/all")
    public List<DatabaseInstance> getAll(@RequestHeader("X-User-Email") String email,@RequestHeader("X-User-Role") String role){
        return service.getUserDatabases(email,role);
    }
    
}
