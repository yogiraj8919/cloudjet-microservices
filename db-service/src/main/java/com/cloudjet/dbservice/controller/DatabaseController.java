package com.cloudjet.dbservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudjet.dbservice.dto.CreateDatabaseRequest;
import com.cloudjet.dbservice.dto.DatabaseResponse;
import com.cloudjet.dbservice.service.DatabaseService;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    private static final Logger log = LoggerFactory.getLogger(DatabaseController.class);
    
    private final DatabaseService service;

    public DatabaseController(DatabaseService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateDatabaseRequest request,@RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(service.create(request, email));
       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestHeader("X-User-Email") String email, @RequestHeader("X-User-Role") String role){
        service.delete(id,email,role);
        return ResponseEntity.ok("Database deletion started");
    }

    @GetMapping("/{id}")
    public DatabaseResponse getById(@PathVariable Long id, @RequestHeader("X-User-Email") String email, @RequestHeader("X-User-Role") String role){
        log.info("Incoming request: GET /db/{} by user {}", id, email);
        return service.getById(id, email, role);
    }

    @GetMapping("/all")
    public List<DatabaseResponse> getAll(@RequestHeader("X-User-Email") String email,@RequestHeader("X-User-Role") String role){
        log.info("Incoming request: GET /db/all by user {}", email);
        return service.getUserDatabases(email,role);
    }
    
}
