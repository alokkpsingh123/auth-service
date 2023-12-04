package org.alok.authservice.service;

import org.alok.authservice.config.CustomUserDetails;
import org.alok.authservice.entity.UserCredential;
import org.alok.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredentialOptional = repository.findByUserName(username);
        return userCredentialOptional.map(
                CustomUserDetails::new
        ).orElseThrow(() ->
        new UsernameNotFoundException("user not found with name: " + username));
    }
}
