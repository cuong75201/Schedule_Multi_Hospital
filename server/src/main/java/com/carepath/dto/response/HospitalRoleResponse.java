package com.carepath.dto.response;

import java.util.UUID;

import com.carepath.entities.enums.StaffRole;

import lombok.Builder;

@Builder
public record HospitalRoleResponse(UUID hospitalId, String hospitalName, StaffRole role) {

}
