package com.carepath.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carepath.dto.request.LoginRequest;
import com.carepath.dto.request.RegisterRequest;
import com.carepath.dto.response.ApiResponse;
import com.carepath.dto.response.LoginResponse;
import com.carepath.dto.response.RegisterResponse;
import com.carepath.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> loginAccount(@Valid @RequestBody LoginRequest request) {
        LoginResponse res = this.authService.authenticate(request);
        return ApiResponse.<LoginResponse>builder().result(res).build();
    }

    @PostMapping("/login-admin")
    public ApiResponse<LoginResponse> loginAdminAccount(@Valid @RequestBody LoginRequest request) {
        LoginResponse res = this.authService.adminAuthenticate(request);
        return ApiResponse.<LoginResponse>builder().result(res).build();
    }

}
