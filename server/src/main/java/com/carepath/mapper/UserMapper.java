package com.carepath.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.carepath.dto.request.PatientInfoRequest;
import com.carepath.dto.request.RegisterRequest;
import com.carepath.dto.response.RegisterResponse;
import com.carepath.entities.PatientEntity;
import com.carepath.entities.UserAccountEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserAccountEntity toUseAccountEntity(RegisterRequest request);

    PatientEntity toPatientEntity(PatientInfoRequest request);

    @Mapping(source = "id", target = "accountId")
    RegisterResponse toRegisterResponse(UserAccountEntity accountEntity);
}
