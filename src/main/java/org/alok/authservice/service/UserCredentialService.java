package org.alok.authservice.service;

import org.alok.authservice.entity.UserCredential;
import org.alok.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserCredentialService {

    @Autowired
    UserCredentialRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public UserCredential saveUserCredential(UserCredential userCredential) {
        return repository.save(userCredential);
    }

    public List<UserCredential> getAllUserCredentials() {
        List<UserCredential> userCredentials = new ArrayList<>();
        for( UserCredential user: repository.findAll()){
            userCredentials.add(user);
        }
        return userCredentials;
    }

    public Optional<UserCredential> getUserCredentialById(String userId) {
        return repository.findById(userId);
    }

    public Optional<UserCredential> getUserCredentialByUsername(String userName) {
        return repository.findByUserName(userName);
    }

    public void deleteUserCredentialById(String userId) {
        repository.deleteById(userId);
    }

    public boolean isValidUser(String userName, String enteredPassword) {
        Optional<UserCredential> userCredentialOptional = repository.findByUserName(userName);

        return userCredentialOptional.map(userCredential ->
                passwordEncoder.matches(enteredPassword, userCredential.getUserPassword())
        ).orElse(false);
    }

    public Optional<String> findUserEmailByUserName(String userName){
        return repository.findUserEmailByUserName(userName);
    }

    public Optional<String> findUserIdByUserName(String userName){
        return repository.findUserIdByUserName(userName);

    }

    public boolean isEmailRegistered(String userEmail){
        return repository.isEmailRegistered(userEmail);
    }

    public Optional<String> findUserEmailByUserId( String userId){
        return repository.findUserEmailByUserId(userId);
    }

    public boolean isUsernameTaken(String username) {
        return repository.findByUserName(username).isPresent();
    }


}
