package com.carepath.mapper;

import org.mapstruct.Mapper;

import com.carepath.dto.request.AdminRegisterRequest;
import com.carepath.dto.response.AdminRegisterResponse;
import com.carepath.entities.PlatformAdminEntity;

@Mapper (componentModel="spring")
public interface PlatformAdminMapper {

    PlatformAdminEntity toPlatformAdminEntity(AdminRegisterRequest request);
    AdminRegisterResponse toAdminRegisterResponse(PlatformAdminEntity admin);
} 
