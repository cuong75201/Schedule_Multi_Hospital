package com.carepath.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
@Builder 
public record RegisterResponse(
    UUID accountId,
    String username,
    boolean verified
) {}
