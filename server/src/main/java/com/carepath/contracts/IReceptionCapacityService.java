package com.carepath.contracts;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * IReceptionCapacityService: Quản lý việc khai báo, điều chỉnh sức chứa (capacity),
 * mở/đóng khả năng tiếp nhận của từng bệnh viện theo ca, thời gian và dịch vụ;
 * đảm bảo một khung giờ có thể tiếp nhận nhiều bệnh nhân mà không vượt ngưỡng quy định.
 *
 * Các Rule tương ứng: BR-CORE-01, BR-CORE-02, BR-CORE-07, BR-CORE-11
 * - BR-CORE-01: Bệnh viện tự quyết định năng lực tiếp nhận (khởi tạo khung tiếp nhận và capacity).
 * - BR-CORE-02: Một khoảng thời gian có thể tiếp nhận nhiều bệnh nhân (capacity >= 1).
 * - BR-CORE-07: Bệnh viện có quyền mở, đóng và điều chỉnh khả năng tiếp nhận (sửa capacity, mở/đóng khung hoặc tạm dừng booking).
 * - BR-CORE-11: Hệ thống chỉ làm trung gian điều phối đăng ký; việc đóng khung chỉ chặn booking mới, không tự ý hủy lịch đã xác nhận hay can thiệp quy trình khám nội bộ.
 *
 * Các bảng liên quan:
 * - Bảng chính: ReceptionWindow (*), CapacityCounter (*)
 * - Bảng tham chiếu & log: DoctorSchedule, Service, Department, Hospital, AuditLog
 * 
 * Chỉnh sửa lại như sau:
 * - Thuộc tính nhận vào ít nhất có thể
 * - Tạo một api/utility gì đó (generateReceptionWindow) để lấy thông tin chính xác từ các service khác thay vì nhập thủ công.
 * Bsi/nhân viên vẫn có thể sửa các bảng ghi đã tồn tại
 * 1. Controller gọi method generateReceptionWindow -> generateReceptionWindow gọi các service khác để tính toán/lấy dữ liệu
 * -> trả về cho controller -> controller gọi tới các method chính (cần ít tham số hơn) -> làm việc với DB
 */
public interface IReceptionCapacityService {

    /**
     * Khởi tạo một khung tiếp nhận mới và bộ đếm sức chứa (CapacityCounter) tương ứng.
     * Giải quyết: BR-CORE-01, BR-CORE-02
     */
    ReceptionWindow createReceptionWindow(
            String hospitalId,
            String departmentId,
            String serviceId,
            String doctorId,
            String scheduleId,
            LocalDate windowDate,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int capacity,
            int overbookingLimit
    );

    /**
     * Điều chỉnh sức chứa (capacity), giới hạn đặt vượt hoặc khung giờ tiếp nhận của một window đã tồn tại.
     * Tự động kiểm tra ràng buộc (newCapacity + newOverbookingLimit >= bookedCount) và cập nhật trạng thái (OPEN / FULL).
     * Giải quyết: BR-CORE-01, BR-CORE-02, BR-CORE-07
     */
    ReceptionWindow updateWindowCapacity(
            String hospitalId,
            String windowId,
            LocalDateTime newStartTime,
            LocalDateTime newEndTime,
            int newCapacity,
            int newOverbookingLimit
    );

    /**
     * Chủ động mở lại (OPEN) hoặc đóng (CLOSED) một khung tiếp nhận cụ thể.
     * Khi chuyển sang CLOSED, hệ thống chỉ ngừng nhận booking mới, giữ nguyên các lịch BOOKED hiện có.
     * Giải quyết: BR-CORE-07, BR-CORE-11
     */
    ReceptionWindow changeWindowStatus(
            String hospitalId,
            String windowId,
            String targetStatus // OPEN | CLOSED
    );

    /**
     * Tạm dừng hoặc mở lại toàn bộ khả năng tiếp nhận đăng ký mới của một bệnh viện
     * (dùng khi bệnh viện quá tải hoặc bị khóa nhận booking do công nợ quá hạn - SuperAdmin).
     * Giải quyết: BR-CORE-07, BR-CORE-11
     */
    HospitalBookingSuspension suspendHospitalBookings(
            String hospitalId,
            boolean isSuspended
    );

    /**
     * Kiểm tra năng lực tiếp nhận và số chỗ còn trống thực tế của một khung giờ.
     * Giải quyết: BR-CORE-01, BR-CORE-02
     */
    CapacityAvailability getWindowAvailability(
            String hospitalId,
            String windowId
    );

    // =========================================================================
    // RESPONSE DTOs
    // =========================================================================

    record ReceptionWindow(
            String id,
            String hospitalId,
            String departmentId,
            String serviceId,
            String doctorId,
            String scheduleId,
            LocalDate windowDate,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int capacity,
            int overbookingLimit,
            int bookedCount,
            int remainingSlots,
            String status, // OPEN, CLOSED, FULL
            LocalDateTime updatedAt
    ) {}

    record CapacityAvailability(
            String windowId,
            String status,
            int capacity,
            int overbookingLimit,
            int bookedCount,
            int remainingStandardSlots,
            int remainingOverbookingSlots,
            boolean isBookable
    ) {}

    record HospitalBookingSuspension(
            String hospitalId,
            boolean isSuspended,
            int affectedFutureWindowsCount,
            String reason,
            LocalDateTime updatedAt
    ) {}
}