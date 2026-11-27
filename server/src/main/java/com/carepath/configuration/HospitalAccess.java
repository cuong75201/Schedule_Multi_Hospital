package com.carepath.configuration;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.carepath.entities.enums.EntityStatus;
import com.carepath.entities.enums.StaffRole;
import com.carepath.repository.HospitalStaffRepository;

import lombok.RequiredArgsConstructor;

@Component ("hospitalAccess")
@RequiredArgsConstructor 
public class HospitalAccess {
    private final HospitalStaffRepository staffRepository;

    public boolean hasAnyRole(UUID hospitalId, Authentication authentication,
                              String... roleNames) {
        if (hospitalId == null
                || !(authentication instanceof JwtAuthenticationToken token)
                || !token.isAuthenticated()) {
            return false;
        }

        var jwt = token.getToken();
        String username = jwt.getSubject(); // sub hiện là username trong CarePath

        if (!"USER".equals(jwt.getClaimAsString("accountType"))
                || username == null || username.isBlank() || roleNames.length == 0) {
            return false;
        }

        var roles = Arrays.stream(roleNames).map(StaffRole::valueOf).toList();

        return staffRepository
                .existsByHospital_IdAndUserAccount_UsernameAndRoleInAndStatusAndUserAccount_Status(
                        hospitalId, username, roles, EntityStatus.ACTIVE, EntityStatus.ACTIVE);
    }
}