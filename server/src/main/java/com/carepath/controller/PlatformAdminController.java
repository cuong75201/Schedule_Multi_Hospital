package com.carepath.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.carepath.dto.request.AdminRegisterRequest;
import com.carepath.dto.response.AdminRegisterResponse;
import com.carepath.dto.response.ApiResponse;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.service.PlatformAdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Controller
public class PlatformAdminController {
    private final PlatformAdminService adminService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<AdminRegisterResponse>> registerAdmin(
            @RequestBody @Valid AdminRegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRM_MISMATCHING);
        }

        AdminRegisterResponse result = adminService.createAdminAccount(request);
        ApiResponse<AdminRegisterResponse> body = ApiResponse.<AdminRegisterResponse>builder().result(result).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
