package com.carepath.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carepath.dto.request.RegisterRequest;
import com.carepath.dto.response.ApiResponse;
import com.carepath.dto.response.RegisterResponse;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class AccountController {
    private final AccountService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<RegisterResponse>> registerAccount(
            @RequestBody @Valid RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new AppException(ErrorCode.PASSWORD_CONFIRM_MISMATCHING);
        }

        RegisterResponse result = userService.createUser(request);

        ApiResponse<RegisterResponse> body = ApiResponse.<RegisterResponse>builder()
                .result(result)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
