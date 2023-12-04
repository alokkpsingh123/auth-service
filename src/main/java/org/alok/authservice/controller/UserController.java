package org.alok.authservice.controller;

import org.alok.authservice.entity.UserCredential;
import org.alok.authservice.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserCredentialService service;


    @GetMapping("/get-all-user")
    public List<UserCredential> getAllUserCredentials() {

        return service.getAllUserCredentials();
    }

    @GetMapping("/get-user-by-id/{userId}")
    public Optional<UserCredential> getUserCredentialById(@PathVariable String userId) {
        return service.getUserCredentialById(userId);
    }

    @GetMapping("/get-user-by-username/{userName}")
    public Optional<UserCredential> getUserCredentialByUsername(@PathVariable String userName) {
        return service.getUserCredentialByUsername(userName);
    }

    @DeleteMapping("/delete-user-by-id/{userId}")
    public void deleteUserCredentialById(@PathVariable  String userId) {
        service.deleteUserCredentialById(userId);
    }


    @GetMapping("/get-useremail-by-userid/{userId}")
    public Optional<String> findUserEmailByUserId(@PathVariable String userId){
        return service.findUserEmailByUserId(userId);
    }
}
