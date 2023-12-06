package org.alok.authservice.controller;


import org.alok.authservice.dto.AuthRequest;
import org.alok.authservice.dto.ResponseTokenDto;
import org.alok.authservice.dto.Response;
import org.alok.authservice.entity.UserCredential;
import org.alok.authservice.service.AuthService;
import org.alok.authservice.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<?> addNewUser(@RequestBody UserCredential userCredential) {
        Response response = new Response();
        try {
            validateUserCredential(userCredential);

            if (userCredentialService.isEmailRegistered(userCredential.getUserEmail())) {
                response.setError("Email '" + userCredential.getUserEmail() + "' is already registered.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            response.setMessage(authService.saveUser(userCredential));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.setError("Invalid request: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setError("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private void validateUserCredential(UserCredential userCredential) {
        if (userCredential.getUserEmail() ==  "" &&
                userCredential.getUserPassword() == "" &&
                userCredential.getUserName() == "") {
            throw new IllegalArgumentException("One or more required fields are empty");
        }
    }

    private void validateAuthRequest(AuthRequest authRequest) {
        if (authRequest.getUserName() ==  "" && authRequest.getUserPassword() == "" ) {
            throw new IllegalArgumentException("One or more required fields are empty");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseTokenDto> getToken(@RequestBody AuthRequest authRequest) {
        ResponseTokenDto responseTokenDto = new ResponseTokenDto();


        try {
            validateAuthRequest(authRequest);

            if (userCredentialService.isValidUser(authRequest.getUserName(), authRequest.getUserPassword())) {
                String userEmail = userCredentialService.findUserEmailByUserName(authRequest.getUserName()).get();
                String token = authService.generateToken(authRequest.getUserName());
                String userId = userCredentialService.findUserIdByUserName(authRequest.getUserName()).get();

                responseTokenDto.setUserName(authRequest.getUserName());
                responseTokenDto.setToken(token);
                responseTokenDto.setUserId(userId);
                return ResponseEntity.ok(responseTokenDto);
            } else {
                responseTokenDto.setError("Unauthorized user");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseTokenDto);
            }
        }catch (IllegalArgumentException e) {
            responseTokenDto.setError("Invalid request: " + e.getMessage());
            return ResponseEntity.badRequest().body(responseTokenDto);
        } catch (Exception e) {
            responseTokenDto.setError("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseTokenDto);
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Response> validateToken(@RequestParam String token) {
        Response response = new Response();

        try {
            authService.validateToken(token);
            response.setMessage("Valid Token");
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            response.setError("An error occurred during token validation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
