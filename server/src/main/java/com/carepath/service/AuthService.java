package com.carepath.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.carepath.configuration.JwtHelper;
import com.carepath.configuration.UserDetailsImpl;
import com.carepath.dto.request.LoginRequest;
import com.carepath.dto.response.AboutMeResponse;
import com.carepath.dto.response.LoginResponse;
import com.carepath.dto.response.HospitalRoleResponse;
import com.carepath.entities.PlatformAdminEntity;
import com.carepath.entities.UserAccountEntity;
import com.carepath.entities.enums.AccountType;
import com.carepath.entities.enums.EntityStatus;
import com.carepath.exception.AppException;
import com.carepath.exception.ErrorCode;
import com.carepath.repository.HospitalStaffRepository;
import com.carepath.repository.PlatformAdminRepository;
import com.carepath.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserAccountRepository userRespository;
    private final PlatformAdminRepository adminRepository;
    private final HospitalStaffRepository hospitalStaffRepository;
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

    public AboutMeResponse aboutMe(Jwt jwt) {
        System.out.print("Token is :" + jwt);

        if (jwt == null || jwt.getSubject() == null)
            throw new AppException(ErrorCode.UNAUTHENTICATE);

        UUID accountId;
        try {
            accountId = UUID.fromString(jwt.getSubject());
        } catch (IllegalArgumentException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATE);
        }
        System.out.print("UUID hhợp lệ");
        String accountType = jwt.getClaimAsString("accountType");
        if ("SUPERADMIN".equals(accountType)) {
            PlatformAdminEntity admin = adminRepository.findById(accountId)
                    .filter(a -> a.getStatus() == EntityStatus.ACTIVE)
                    .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATE));
            return new AboutMeResponse(
                    admin.getId(), admin.getUsername(), AccountType.SUPERADMIN, List.of());
        }
        if (!"USER".equals(accountType)) {
            throw new AppException(ErrorCode.UNAUTHENTICATE);
        }
        UserAccountEntity user = userRespository.findById(accountId)
                .filter(u -> u.getStatus() == EntityStatus.ACTIVE)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATE));

        var hospitalRoles = hospitalStaffRepository
                .findAllByUserAccount_IdAndStatus(accountId, EntityStatus.ACTIVE)
                .stream()
                .map(staff -> new HospitalRoleResponse(
                        staff.getHospital().getId(),
                        staff.getHospital().getName(),
                        staff.getRole()))
                .toList();

        return new AboutMeResponse(
                user.getId(), user.getUsername(), AccountType.USER, hospitalRoles);
    }

}
