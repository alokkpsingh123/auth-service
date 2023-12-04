package org.alok.authservice.repository;

import org.alok.authservice.entity.UserCredential;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends CrudRepository<UserCredential, String> {

    Optional<UserCredential> findByUserName(String userName);

    Optional<String> findUserEmailByUserId(String userId);

}
