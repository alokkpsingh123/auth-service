package org.alok.authservice.controller;


import org.alok.authservice.dto.AuthRequest;
import org.alok.authservice.entity.UserCredential;
import org.alok.authservice.repository.UserCredentialRepository;
import org.alok.authservice.service.AuthService;
import org.alok.authservice.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserCredentialService userCredentialService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential userCredential){
        return authService.saveUser(userCredential);
    }

    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequest authRequest){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authRequest.getUserName(),
//                        authRequest.getPassword()
//                )
//        );


        if( userCredentialService.isValidUser(authRequest.getUserName(),authRequest.getUserPassword())){
            return authService.generateToken(authRequest.getUserName());
        }else {
            throw new RuntimeException("Invalid access");
        }


    }

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String token){
        authService.validateToken(token);
        return "valid token";
    }

}
