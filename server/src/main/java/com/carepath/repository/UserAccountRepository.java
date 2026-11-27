package com.carepath.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carepath.entities.UserAccountEntity;

@Repository 
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, UUID>{
    Optional<UserAccountEntity> findByUsername(String username);
    
}