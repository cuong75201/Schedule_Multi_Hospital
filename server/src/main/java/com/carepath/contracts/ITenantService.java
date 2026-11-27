package com.carepath.contracts;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * ITenantService: Quản lý thông tin bệnh viện (Tenant), khoa/phòng ban, danh mục dịch vụ y tế
 * và cấu hình chính sách đặt/hủy/đổi lịch, check-in riêng biệt cho từng bệnh viện.
 *
 * Các bảng liên quan:
 * - Bảng chính: Hospital (*), Department (*), Service (*), HospitalPolicy (*)
 * - Bảng log: AuditLog
 */
public interface ITenantService {

        /**
         * Khởi tạo mới một bệnh viện trên hệ thống.
         * @param code
         * @param name
         * @param address
         * @param phone
         * @param timezone
         * @return Hospital
         */
        Hospital createHospital(
                String code,
                String name,
                String address,
                String phone,
                String timezone // Mặc định "Asia/Ho_Chi_Minh" nếu null
        );

        /**
         * Cập nhật thông tin hành chính hoặc trạng thái hoạt động của bệnh viện (tách từng biến từ patch)
         * @param hospitalId
         * @param name
         * @param address
         * @param phone
         * @param timezone
         * @param status
         * @return Hospital
         */
        Hospital updateHospital(
                String hospitalId,
                String name,
                String address,
                String phone,
                String timezone,
                String status // ACTIVE | INACTIVE | SUSPENDED
        );

        /**
         * Lấy thông tin chi tiết bệnh viện
         * @param hospitalId
         * @return Hospital
         */
        Hospital getHospitalScope(
                String hospitalId
        );


        /**
         * Tạo mới một khoa/phòng ban trực thuộc bệnh viện
         * @param hospitalId
         * @param code
         * @param name
         * @return Department
         */
        Department createDepartment(
                String hospitalId,
                String code,
                String name
        );

        /**
         * Tạo mới một dịch vụ khám/y tế của bệnh viện (có thể gắn với một khoa hoặc để trống departmentId).
         * @param hospitalId
         * @param departmentId
         * @param code
         * @param name
         * @param durationMin
         * @param price
         * @param requiresDoctorSelection
         * @return Service
         */
        Service createService(
                String hospitalId,
                String departmentId, // Có thể null
                String code,
                String name,
                Integer durationMin, // Có thể null
                BigDecimal price,    // Có thể null
                boolean requiresDoctorSelection
        );

        /**
        * hàm tiện ích: lấy thông tin của service
        * @param serviceId
        * @return Service
        */
        Service getService(
                String serviceId
        );

        /**
         * Cấu hình dịch vụ có bắt buộc/cho phép bệnh nhân chọn bác sĩ khi đặt lịch hay không.
         * @param hospitalId
         * @param serviceId
         * @param requiresDoctorSelection
         * @return Service
         */
        Service setServiceDoctorSelection(
                String hospitalId,
                String serviceId,
                boolean requiresDoctorSelection
        );


        /**
         * Khởi tạo hoặc cập nhật chính sách cho bệnh viện dựa vào cặp (hospitalId, serviceId):
         * 1. Nếu cặp (hospitalId, serviceId) đã tồn tại -> cập nhật/ghi đè.
         * 2. Nếu chưa tồn tại -> tạo bản ghi mới (serviceId = null là chính sách mặc định của bệnh viện).
         * Ghi nhận thay đổi cấu hình vào AuditLog. (Trường priorityPolicyEnabled mặc định gán false).
         * @param hospitalId
         * @param serviceId
         * @param bookingAdvanceDays
         * @param allowOverbooking
         * @param overbookingLimitPct
         * @param preventDuplicateBooking
         * @param checkinEarlyMin
         * @param checkinLateMin
         * @param cancelCutoffMin
         * @param allowSelfCancelAfterCutoff
         * @param noShowAfterMin
         * @return HospitalPolicy
         */
        HospitalPolicy upsertHospitalPolicy(
                String hospitalId,
                String serviceId, // Null nếu là chính sách mặc định toàn bệnh viện
                int bookingAdvanceDays,
                boolean allowOverbooking,
                BigDecimal overbookingLimitPct,
                boolean preventDuplicateBooking,
                int checkinEarlyMin,
                int checkinLateMin,
                int cancelCutoffMin,
                boolean allowSelfCancelAfterCutoff,
                int noShowAfterMin
        );

        /**
         * Lấy chính sách đang có hiệu lực thực tế theo cơ chế Fallback:
         * 1. Tìm bản ghi có (hospitalId, serviceId).
         * 2. Nếu không có cấu hình riêng cho dịch vụ (hoặc serviceId == null), trả về bản ghi mặc định (hospitalId, NULL).
         * @param hospitalId
         * @param serviceId
         * @return HospitalPolicy
         */
        HospitalPolicy resolvePolicy(
                String hospitalId,
                String serviceId
        );

        /**
         * Lấy toàn bộ danh sách chính sách của một bệnh viện (gồm policy mặc định và các policy theo từng dịch vụ).
         * @param hospitalId
         * @return List<HospitalPolicy>
         */
        List<HospitalPolicy> getAllPoliciesByHospital(
                String hospitalId
        );

        /**
         * Xóa chính sách ghi đè của một dịch vụ để dịch vụ đó quay về áp dụng chính sách mặc định của bệnh viện.
         * (Không cho phép xóa chính sách mặc định khi serviceId = null).
         * @param hospitalId
         * @param serviceId
         */
        void deleteServicePolicyOverride(
                String hospitalId,
                String serviceId
        );

        /**
         * Hàm tiện ích kiểm tra quy tắc thời gian động (Read-only Validation), KHÔNG thêm/sửa/xóa dữ liệu trong database.
         *
         * Mục đích: Tập trung toàn bộ logic so sánh chính sách thời gian vào một nơi duy nhất; trả về kết quả
         * cho phép hay từ chối kèm mã lỗi và lý do cụ thể.
         *
         * Ai gọi và dùng khi nào? Được gọi nội bộ ở bước kiểm tra đầu vào bởi các Service nghiệp vụ khác
         * (BookingService với actionType = "BOOK", AppointmentService với "CANCEL", CheckInService với "CHECK_IN")
         * trước khi thực hiện thay đổi dữ liệu.
         * @param hospitalId
         * @param serviceId
         * @param actionType
         * @param actionTime
         * @return PolicyCheckResult
         */
        PolicyCheckResult validatePolicyRule(
                String hospitalId,
                String serviceId,
                String actionType, // BOOK | CANCEL | CHECK_IN
                ZonedDateTime actionTime    // Thời gian thực hiện hành vi lúc đó
        );


        record Hospital(
                String id,
                String code,
                String name,
                String address,
                String phone,
                String timezone,
                String status, // ACTIVE | INACTIVE | SUSPENDED
                ZonedDateTime createdAt,
                ZonedDateTime updatedAt
        ) {}

        record Department(
                String id,
                String hospitalId,
                String code,
                String name,
                String status // ACTIVE | INACTIVE | SUSPENDED
        ) {}

        record Service(
                String id,
                String hospitalId,
                String departmentId,
                String code,
                String name,
                Integer durationMin,
                BigDecimal price,
                boolean requiresDoctorSelection,
                String status // ACTIVE | INACTIVE | SUSPENDED
        ) {}

        record HospitalPolicy(
                String id,
                String hospitalId,
                String serviceId,   // null nếu là chính sách mặc định
                int bookingAdvanceDays,
                boolean allowOverbooking,
                BigDecimal overbookingLimitPct,
                boolean preventDuplicateBooking,
                int checkinEarlyMin,
                int checkinLateMin,
                int cancelCutoffMin,
                boolean allowSelfCancelAfterCutoff,
                int noShowAfterMin,
                boolean priorityPolicyEnabled,
                ZonedDateTime createdAt
        ) {}

        record PolicyCheckResult(
                boolean allowed,
                String violatedRuleCode, // VD: CANCEL_CUTOFF_EXCEEDED, CHECKIN_TOO_EARLY, BOOKING_TOO_FAR...
                String message
        ) {}
}