package com.carepath.dto.response;

import java.util.UUID;

import lombok.Builder;

@Builder 
public record RegisterResponse(
    UUID accountId,
    String username,
    boolean verified
) {}
