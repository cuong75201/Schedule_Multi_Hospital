package com.carepath.contracts;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * IReceptionCapacityService: Quản lý việc khởi tạo, điều chỉnh sức chứa (capacity) và mở/đóng
 * các khung tiếp nhận bệnh nhân (ReceptionWindow) theo ca làm việc, dịch vụ và chính sách bệnh viện.
 *
 * Các bảng liên quan:
 * - Bảng chính: ReceptionWindow (*), CapacityCounter (*)
 * - Bảng tham chiếu & log: DoctorSchedule, HospitalPolicy, Service, AuditLog
 */
public interface IReceptionCapacityService {

        /**
         * Hàm tiện ích (Read-only, không lưu DB): gọi để lấy các thông tin cần thiết khác từ các table khác
         * Giảm truyền nhiều tham số vào methods openReceptionWindow
         * @param scheduleId
         * @param serviceId
         * @return ReceptionWindowDraft
         */
        ReceptionWindowDraft generateReceptionWindows(
                String scheduleId,
                String serviceId
        );

        /**
         * Khởi tạo chính thức một khung tiếp nhận (ReceptionWindow) ở trạng thái OPEN,
         * đồng thời tạo bản ghi CapacityCounter(windowId, bookedCount = 0, version = 0).
         * Các trường hospitalId, departmentId, doctorId, windowDate được tự động suy ra từ scheduleId/serviceId
         * Các trường time được suy ra từ scheduleId
         * Trường overbookingLimit được suy ra từ HospitalPolicy.overbookingLimitPct
         * @param scheduleId
         * @param serviceId
         * @param capacity
         * @return ReceptionWindow
         */
        ReceptionWindow openReceptionWindow(
                String scheduleId,
                String serviceId,
                int capacity
        );

        /**
         * Điều chỉnh sức chứa tiêu chuẩn và giới hạn đặt vượt của một khung tiếp nhận đã tồn tại.
         * Tự động kiểm tra ràng buộc (capacity + overbookingLimit >= bookedCount) -> cập nhật trạng thái OPEN, ngược lại FULL
         * @param windowId
         * @param capacity
         * @param overbookingLimit
         * @return ReceptionWindow
         */
        ReceptionWindow updateWindowCapacity(
                String windowId,
                int capacity,
                int overbookingLimit
        );

        /**
         * Đóng khung tiếp nhận (chuyển status sang CLOSED) và ghi nhận lý do vào AuditLog.
         * Chỉ chặn đăng ký mới, giữ nguyên các lịch BOOKED hiện hữu.
         * @param windowId
         * @param reason
         * @return ReceptionWindow
         */
        ReceptionWindow closeWindow(
                String windowId,
                String reason
        );

        /**
         * Mở lại khung tiếp nhận đang đóng (tự động chuyển về OPEN nếu còn chỗ, hoặc FULL nếu đã đủ tải).
         * @param windowId
         * @return ReceptionWindow
         */
        ReceptionWindow reopenWindow(
                String windowId
        );

        /**
         * Cập nhật thủ công trạng thái của khung tiếp nhận (OPEN | CLOSED | FULL).
         * @param windowId
         * @param status
         * @return ReceptionWindow
         */
        ReceptionWindow setWindowStatus(
                String windowId,
                String status // OPEN | CLOSED | FULL (khớp WindowStatus enum)
        );

        /**
         * Lấy thông tin chi tiết một khung tiếp nhận theo ID.
         * @param windowId
         * @return ReceptionWindow
         */
        ReceptionWindow getWindow(
                String windowId
        );

        /**
         * Lấy danh sách các khung tiếp nhận đang mở (OPEN) theo bệnh viện, dịch vụ và ngày khám để bệnh nhân đặt lịch.
         * @param hospitalId
         * @param serviceId
         * @param windowDate
         * @return List<ReceptionWindow>
         */
        List<ReceptionWindow> listOpenWindows(
                String hospitalId,
                String serviceId,
                ZonedDateTime windowDate
        );

        /**
         * Hàm tiện ích: Tính toán năng lực tiếp nhận thực tế và số suất còn trống của một khung giờ từ CapacityCounter.
         * @param windowId
         * @return EffectiveCapacity
         */
        EffectiveCapacity getEffectiveCapacity(
                String windowId
        );

        record ReceptionWindowDraft(
                String hospitalId,
                String departmentId,
                String serviceId,
                String doctorId,
                String scheduleId,
                ZonedDateTime windowDate,
                ZonedDateTime startTime,
                ZonedDateTime endTime,
                int suggestedCapacity,
                int suggestedOverbookingLimit
        ) {}

        record ReceptionWindow(
                String id,
                String hospitalId,
                String departmentId,
                String serviceId,
                String doctorId,
                String scheduleId,
                ZonedDateTime windowDate,
                ZonedDateTime startTime,
                ZonedDateTime endTime,
                int capacity,
                int overbookingLimit,
                String status, // OPEN | CLOSED | FULL
                ZonedDateTime createdAt,
                ZonedDateTime updatedAt
        ) {}

        record      EffectiveCapacity(
                String windowId,
                int capacity,
                int overbookingLimit,
                int bookedCount,
                int available // = (capacity + overbookingLimit) - bookedCount
        ) {}
}