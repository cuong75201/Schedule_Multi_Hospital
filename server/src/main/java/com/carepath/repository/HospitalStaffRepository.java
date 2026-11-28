package com.carepath.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.carepath.entities.HospitalStaffEntity;
import com.carepath.entities.enums.EntityStatus;
import com.carepath.entities.enums.StaffRole;

public interface HospitalStaffRepository extends JpaRepository<HospitalStaffEntity, UUID> {

    boolean existsByHospital_IdAndUserAccount_UsernameAndRoleInAndStatusAndUserAccount_Status(
            UUID hospitalId,
            String username,
            Collection<StaffRole> roles,
            EntityStatus staffStatus,
            EntityStatus accountStatus);

    @EntityGraph(attributePaths = "hospital")
    List<HospitalStaffEntity> findAllByUserAccount_IdAndStatus(
            UUID accountId, EntityStatus status);
}
