package com.carepath.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carepath.entities.PlatformAdminEntity;

@Repository 
public interface PlatformAdminRepository extends JpaRepository<PlatformAdminEntity,UUID>{
    Optional<PlatformAdminEntity> findByUsername(String username);
}
