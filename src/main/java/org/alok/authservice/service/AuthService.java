package org.alok.authservice.service;

import org.alok.authservice.entity.UserCredential;
import org.alok.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserCredentialRepository repository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    public String saveUser(UserCredential userCredential){

        userCredential.setUserPassword(passwordEncoder.encode(userCredential.getUserPassword()));
        repository.save(userCredential);
        return "user registered";
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
