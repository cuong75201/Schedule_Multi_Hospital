package com.carepath.contracts;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import com.carepath.entities.enums.EntityStatus;
import com.carepath.entities.enums.ScheduleStatus;
import com.carepath.entities.enums.Shift;

/**
 * IDoctorSchedulingService: Quản lý hồ sơ bác sĩ cấp hệ thống, liên kết bác sĩ với nhiều bệnh viện,
 * phân công phạm vi dịch vụ chuyên môn và quản lý ca làm việc (lịch công tác / nghỉ phép).
 *
 * Các bảng liên quan:
 * - Bảng chính: Doctor (*), DoctorHospital (*), DoctorService (*), DoctorSchedule (*)
 * - Bảng tham chiếu & log: Hospital, Department, Service, AuditLog
 */
public interface IDoctorSchedulingService {

    /**
     * Khởi tạo hồ sơ bác sĩ mới ở cấp toàn hệ thống.
     * @param fullName
     * @param licenseNo
     * @param phone
     * @param email
     * @return Doctor
     */
    Doctor createDoctor(
            String fullName,
            String licenseNo, // Có thể null
            String phone,     // Có thể null
            String email      // Có thể null
    );

    /**
     * Gắn kết bác sĩ vào công tác tại một bệnh viện cụ thể
     * @param doctorId
     * @param hospitalId
     * @return DoctorHospital
     */
    DoctorHospital attachDoctorToHospital(
            String doctorId,
            String hospitalId
    );

    /**
     * Phân công danh sách các dịch vụ khám mà bác sĩ được phép thực hiện tại một bệnh viện.
     * @param doctorId
     * @param hospitalId
     * @param serviceIds
     * @return List<DoctorService>
     */
    List<DoctorService> assignDoctorServices(
            String doctorId,
            String hospitalId,
            List<String> serviceIds 
    );

    /**
     * Khởi tạo ca làm việc (MORNING | AFTERNOON | EVENING) cho bác sĩ tại bệnh viện/khoa theo ngày.
     * @param doctorId
     * @param hospitalId
     * @param departmentId
     * @param scheduleDate
     * @param shift
     * @param startTime
     * @param endTime
     * @return DoctorSchedule
     */
    DoctorSchedule createDoctorSchedule(
            String doctorId,
            String hospitalId,
            String departmentId, // Có thể null
            LocalDate scheduleDate,
            Shift shift,
            ZonedDateTime startTime,
            ZonedDateTime endTime
    );

    /**
     * Đánh dấu ca làm việc của bác sĩ chuyển sang trạng thái nghỉ (LEAVE) kèm lý do (ghi AuditLog).
     * @param scheduleId
     * @param reason
     * @return DoctorSchedule
     */
    DoctorSchedule setScheduleLeave(
            String scheduleId,
            String reason
    );

    /**
     * Lấy danh sách toàn bộ ca làm việc của các bác sĩ tại một bệnh viện theo ngày cụ thể.
     * @param hospitalId
     * @param date
     * @return List<DoctorSchedule>
     */
    List<DoctorSchedule> getSchedulesByHospitalDate(
            String hospitalId,
            ZonedDateTime date
    );

    /**
     * Kiểm tra điều kiện hợp lệ của bác sĩ cho dịch vụ tại bệnh viện (Read-only):
     * đối chiếu DoctorHospital (ACTIVE) + DoctorService tồn tại + có DoctorSchedule (ACTIVE).
     * @param doctorId
     * @param hospitalId
     * @param serviceId
     * @return boolean
     */
    boolean isDoctorAvailableForService(
            String doctorId,
            String hospitalId,
            String serviceId
    );


    record Doctor(
            String id,
            String fullName,
            String licenseNo,
            String phone,
            String email,
            EntityStatus status, // ACTIVE | INACTIVE | SUSPENDED
            ZonedDateTime createdAt
    ) {}

    record DoctorHospital(
            String doctorId,
            String hospitalId,
            EntityStatus status // ACTIVE | INACTIVE | SUSPENDED
    ) {}

    record DoctorService(
            String doctorId,
            String serviceId,
            String hospitalId
    ) {}

    record DoctorSchedule(
            String id,
            String doctorId,
            String hospitalId,
            String departmentId,
            ZonedDateTime scheduleDate,
            Shift shift, // MORNING | AFTERNOON | EVENING
            ZonedDateTime startTime,
            ZonedDateTime endTime,
            ScheduleStatus status // ACTIVE | LEAVE
    ) {}
}