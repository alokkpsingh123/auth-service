package org.alok.authservice.repository;

import org.alok.authservice.entity.UserCredential;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends CrudRepository<UserCredential, String> {

    Optional<UserCredential> findByUserName(String userName);

    @Query("SELECT uc.userEmail FROM UserCredential uc WHERE uc.userName = :userName")
    Optional<String> findUserEmailByUserName(@Param("userName") String userName);

    @Query("SELECT uc.userId FROM UserCredential uc WHERE uc.userName = :userName")
    Optional<String> findUserIdByUserName(@Param("userName") String userName);

    @Query("SELECT COUNT(uc) > 0 FROM UserCredential uc WHERE uc.userEmail = :userEmail")
    boolean isEmailRegistered(@Param("userEmail") String userEmail);

    @Query("SELECT uc.userEmail FROM UserCredential uc WHERE uc.userId = :userId")
    Optional<String> findUserEmailByUserId(@Param("userId") String userId);
}
