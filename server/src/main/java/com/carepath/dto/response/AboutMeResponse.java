package com.carepath.dto.response;

import java.util.List;
import java.util.UUID;

import com.carepath.entities.enums.AccountType;

import lombok.Builder;

@Builder 
    
public record AboutMeResponse( UUID accountId, String username, AccountType accountType, List<HospitalRoleResponse> hospitalRole) {}