package com.carepath.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.carepath.entities.enums.EntityStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
public record AdminRegisterResponse(
        UUID id,
        String username,
        String fullName,
        String email,
        EntityStatus status,
        LocalDateTime createdAt) {
}