package com.carepath.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carepath.dto.request.RegisterRequest;
import com.carepath.dto.response.RegisterResponse;
import com.carepath.entities.PatientEntity;
import com.carepath.entities.UserAccountEntity;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.mapper.UserMapper;
import com.carepath.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AccountService {
    private final UserAccountRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    public RegisterResponse createUser(RegisterRequest request){
        UserAccountEntity user = userMapper.toUseAccountEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        
        PatientEntity patient = request.getPatientInfo() != null ? userMapper.toPatientEntity(request.getPatientInfo()) : new PatientEntity();
        patient.setFullName(request.getFullName());
        user.setPatient(patient);

        try {
            userRepository.save(user);
        } catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return userMapper.toRegisterResponse(user);
    }

    
}
