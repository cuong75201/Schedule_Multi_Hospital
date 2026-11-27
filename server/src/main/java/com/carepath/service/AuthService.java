package com.carepath.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.carepath.configuration.JwtHelper;
import com.carepath.configuration.UserDetailsImpl;
import com.carepath.dto.request.LoginRequest;
import com.carepath.dto.request.LogoutRequest;
import com.carepath.dto.response.LoginResponse;
import com.carepath.entities.UserAccountEntity;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.repository.UserAccountRepository;
import com.nimbusds.jose.JWSHeader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserAccountRepository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public LoginResponse authenticate(LoginRequest request) {
        UserAccountEntity user = userRespository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash()))
            throw new AppException(ErrorCode.UNAUTHENTICATE);

        UserDetailsImpl userDetail = new UserDetailsImpl(user);

        String token = this.jwtHelper.generateJwtToken(userDetail);

        return new LoginResponse(token,true);
    }



}
