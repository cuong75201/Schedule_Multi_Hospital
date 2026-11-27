package com.carepath.contracts;

import java.time.ZonedDateTime;

/**
 * IPatientAccountService: Quản lý hồ sơ hành chính bệnh nhân (Patient) và tài khoản đăng nhập/xác thực (UserAccount).
 *
 * Các bảng liên quan:
 * - Bảng chính: Patient (*), UserAccount (*)
 * - Bảng tham chiếu & log: Appointment, AuditLog
 */
public interface IPatientAccountService {

    /**
     * Khởi tạo hồ sơ hành chính bệnh nhân
     * @param fullName
     * @param dob
     * @param gender
     * @param phone
     * @param email
     * @return Patient
     */
    Patient createPatient(
            String fullName,
            ZonedDateTime dob,  // Có thể null (khớp Patient.dateOfBirth)
            String gender,  // Có thể null
            String phone,
            String email    // Có thể null
    );

    /**
     * Tạo tài khoản đăng nhập cho bệnh nhân (ràng buộc duy nhất 1-1 theo patientId).
     * @param patientId
     * @param username
     * @param passwordHash
     * @return UserAccount
     */
    UserAccount createUserAccount(
            String patientId,
            String username,
            String passwordHash
    );

    /**
     * Xác thực tài khoản bệnh nhân bằng mã OTP hoặc token email (cập nhật isVerified = true, verifiedAt).
     * @param accountId
     * @param otpOrEmailToken
     * @return UserAccount
     */
    UserAccount verifyAccount(
            String accountId,
            String otpOrEmailToken
    );

    /**
     * Ghi nhận việc bệnh nhân xác nhận tự chịu trách nhiệm về tính chính xác của thông tin đã cung cấp (chỉ ghi AuditLog)
     * @param patientId
     * @param accepted
     */
    void confirmDataResponsibility(
            String patientId,
            boolean accepted
    );


    record Patient(
            String id,
            String fullName,
            ZonedDateTime dateOfBirth,
            String gender,
            String phone,
            String email,
            String status, // ACTIVE | INACTIVE | SUSPENDED
            ZonedDateTime createdAt
    ) {}

    record UserAccount(
            String id,
            String patientId,
            String username,
            boolean isVerified,
            ZonedDateTime verifiedAt,
            String status, // ACTIVE | INACTIVE | SUSPENDED
            ZonedDateTime createdAt
    ) {}
}