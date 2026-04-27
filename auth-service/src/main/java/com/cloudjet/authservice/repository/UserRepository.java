package com.cloudjet.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudjet.authservice.entity.User;


public interface UserRepository extends JpaRepository<User,Long>{
    Optional<User> findByEmail(String email);

    
}
