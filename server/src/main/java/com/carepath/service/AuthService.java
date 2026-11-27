package com.carepath.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carepath.configuration.JwtHelper;
import com.carepath.configuration.UserDetailsImpl;
import com.carepath.dto.request.LoginRequest;
import com.carepath.dto.response.LoginResponse;
import com.carepath.entities.PlatformAdminEntity;
import com.carepath.entities.UserAccountEntity;
import com.carepath.entities.enums.AccountType;
import com.carepath.entities.enums.EntityStatus;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.repository.PlatformAdminRepository;
import com.carepath.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserAccountRepository userRespository;
    private final PlatformAdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public LoginResponse authenticate(LoginRequest request) {
        UserAccountEntity user = userRespository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
            throw new AppException(ErrorCode.UNAUTHENTICATE);

        if (user.getStatus() != EntityStatus.ACTIVE) {
            throw new AppException(ErrorCode.UNAUTHENTICATE);
        }
        UserDetailsImpl userDetail = UserDetailsImpl.builder().userAccountEntity(user).accountType(AccountType.USER)
                .build();

        String token = this.jwtHelper.generateJwtToken(userDetail);

        return new LoginResponse(token, true);
    }

    public LoginResponse adminAuthenticate(LoginRequest request) {
        PlatformAdminEntity admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash()))
            throw new AppException(ErrorCode.UNAUTHENTICATE);
        if (admin.getStatus() != EntityStatus.ACTIVE)
            throw new AppException(ErrorCode.UNAUTHENTICATE);

        UserDetailsImpl adminDetail = UserDetailsImpl.builder().adminEntity(admin).accountType(AccountType.SUPERADMIN)
                .build();

        String token = this.jwtHelper.generateJwtToken(adminDetail);
        return new LoginResponse(token, true);

    }

}
