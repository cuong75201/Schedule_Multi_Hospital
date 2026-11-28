package com.carepath.contracts;

import java.util.List;

/**
 * IHospitalStaffService: Quản lý danh sách nhân sự và phân vai trò (ADMIN, STAFF, DOCTOR)
 * trực thuộc từng bệnh viện trên hệ thống.
 *
 * Các bảng liên quan:
 * - Bảng chính: HospitalStaff (*)
 * - Bảng tham chiếu: Hospital, UserAccount
 */
public interface IHospitalStaffService {

    /**
     * Thêm mới một nhân sự vào bệnh viện với vai trò cụ thể (có thể gắn userAccountId hoặc để trống).
     * @param hospitalId
     * @param fullName
     * @param role
     * @param userAccountId
     * @return HospitalStaff
     */
    HospitalStaff addStaff(
            String hospitalId,
            String fullName,
            String role,         // ADMIN | STAFF | DOCTOR (khớp StaffRole enum)
            String userAccountId // Có thể null
    );

    /**
     * Ngừng kích hoạt nhân sự tại bệnh viện (chuyển status sang INACTIVE).
     * @param staffId
     */
    void deactivateStaff(
            String staffId
    );

    /**
     * Lấy danh sách nhân sự của một bệnh viện lọc theo vai trò (ADMIN, STAFF, DOCTOR).
     * @param hospitalId
     * @param role
     * @return List<HospitalStaff>
     */
    List<HospitalStaff> listStaffByRole(
            String hospitalId,
            String role // ADMIN | STAFF | DOCTOR (khớp StaffRole enum)
    );


    record HospitalStaff(
            String id,
            String hospitalId,
            String userAccountId,
            String fullName,
            String role,  // ADMIN | STAFF | DOCTOR
            String status // ACTIVE | INACTIVE | SUSPENDED
    ) {}
}