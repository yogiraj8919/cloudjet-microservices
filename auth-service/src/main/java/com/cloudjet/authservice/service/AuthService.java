package com.cloudjet.authservice.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cloudjet.authservice.config.JwtService;
import com.cloudjet.authservice.dto.LoginRequest;
import com.cloudjet.authservice.dto.RegisterRequest;
import com.cloudjet.authservice.entity.User;
import com.cloudjet.authservice.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    public Map<String,String> login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        if(!bCryptPasswordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invaild password");
        }
        String token = jwtService.generateToken(user.getEmail());
        
        return Map.of("token",token);
    }


}
