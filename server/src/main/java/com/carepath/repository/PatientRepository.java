package com.carepath.repository;

import org.springframework.stereotype.Repository;

import com.carepath.entities.PatientEntity;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository 
public interface PatientRepository  extends JpaRepository<PatientEntity,UUID>{
    
}
