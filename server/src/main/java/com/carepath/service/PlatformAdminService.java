package com.carepath.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carepath.configuration.JwtHelper;
import com.carepath.dto.request.AdminRegisterRequest;
import com.carepath.dto.response.AdminRegisterResponse;
import com.carepath.entities.PlatformAdminEntity;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.mapper.PlatformAdminMapper;
import com.carepath.repository.PlatformAdminRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class PlatformAdminService {
    private final PlatformAdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlatformAdminMapper adminMapper;

    public AdminRegisterResponse createAdminAccount(AdminRegisterRequest request){
        PlatformAdminEntity adminEntity = adminMapper.toPlatformAdminEntity(request);
        adminEntity.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        try{
            adminRepository.save(adminEntity);
        }
        catch(DataIntegrityViolationException exp){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return adminMapper.toAdminRegisterResponse(adminEntity);
    }
}
