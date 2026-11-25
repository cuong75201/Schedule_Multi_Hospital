package com.carepath.contracts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * IAppointmentService: Điều phối vòng đời cốt lõi của lịch khám (đặt lịch, check-in, hủy, đổi lịch, xử lý sự cố).
 *
 * Các Rule CORE: BR-CORE-03, BR-CORE-04, BR-CORE-05, BR-CORE-08, BR-CORE-10, BR-CORE-11
 *
 * Các bảng liên quan: Appointment (*), CheckIn (*), QueueEntry (*), AppointmentStatusHistory (*),
 * ReceptionWindow, CapacityCounter, HospitalPolicy, Service, Doctor, Patient, NotificationOutbox, AuditLog
 */
public interface IAppointmentService {

    /**
     * Tạo mới lịch khám (hỗ trợ cả bệnh nhân đặt Online và nhân viên tạo Walk-in qua SecurityContext).
     * Kiểm tra khung giờ mở, sức chứa (CapacityCounter), chống trùng lịch và lưu trạng thái BOOKED.
     * Giải quyết: BR-CORE-03, BR-CORE-04, BR-CORE-05, BR-CORE-11
     */
    AppointmentDTO createAppointment(
            String hospitalId,
            String patientId,
            String serviceId,
            String windowId,
            String doctorId // Null nếu dịch vụ không yêu cầu chọn bác sĩ (BR-403)
    );

    /**
     * Xác nhận bệnh nhân đã đến cơ sở y tế (Check-in) và tạo lượt trong hàng đợi (QueueEntry).
     * Kiểm tra lịch hợp lệ (BOOKED) và khung thời gian cho phép check-in theo HospitalPolicy.
     * Giải quyết: BR-CORE-05, BR-CORE-10, BR-CORE-11
     */
    CheckInResultDTO checkInAppointment(
            String hospitalId,
            String bookingCode,
            String method, // SELF | STAFF | KIOSK (khớp CheckIn.method)
            String note    // Khớp CheckIn.note
    );

    /**
     * Bệnh nhân hủy lịch khám (kiểm tra thời hạn hủy theo HospitalPolicy) và hoàn trả chỗ vào CapacityCounter.
     * Giải quyết: BR-CORE-09, BR-CORE-11
     */
    AppointmentDTO cancelAppointmentByPatient(
            String patientId,
            String appointmentId,
            String cancelReason
    );

    /**
     * Bệnh nhân đổi lịch sang khung giờ mới: tạo lịch mới (parentAppointmentId) và chuyển lịch cũ sang RESCHEDULED (supersededById).
     * Giải quyết: BR-CORE-03, BR-CORE-11
     */
    AppointmentDTO rescheduleAppointment(
            String patientId,
            String currentAppointmentId,
            String newWindowId,
            String newDoctorId
    );

    /**
     * Bệnh viện xử lý sự cố bất thường (bác sĩ nghỉ, hỏng phòng, quá tải) cho danh sách lịch bị ảnh hưởng.
     * Hỗ trợ 3 hướng: REASSIGN_DOCTOR (đổi bác sĩ), MOVE_WINDOW (dời khung giờ), CANCEL_BY_HOSPITAL (hủy kèm lý do).
     * Giải quyết: BR-CORE-08, BR-CORE-10, BR-CORE-11
     */
    DisruptionHandlingResultDTO handleHospitalDisruption(
            String hospitalId,
            List<String> affectedAppointmentIds,
            String actionType,     // REASSIGN_DOCTOR | MOVE_WINDOW | CANCEL_BY_HOSPITAL
            String targetDoctorId, // Dùng khi đổi bác sĩ (Appointment.doctorId)
            String targetWindowId, // Dùng khi dời khung giờ (Appointment.windowId)
            String reason          // Lưu vào Appointment.cancelReason hoặc AuditLog
    );

    /**
     * Gửi thông báo điều chỉnh thời gian tiếp nhận dự kiến qua NotificationOutbox khi tiến độ khám thực tế bị chậm.
     * Giải quyết: BR-CORE-03, BR-CORE-04, BR-CORE-11
     */
    int notifyEstimatedTimeAdjustment(
            String hospitalId,
            String windowId,
            int estimatedDelayMinutes,
            String messageNote
    );

    /**
     * Đánh dấu lịch hẹn quá hạn check-in thành NO_SHOW; tùy chọn giải phóng chỗ (CapacityCounter) theo chính sách bệnh viện.
     * Giải quyết: BR-CORE-05, BR-CORE-09
     */
    AppointmentDTO markAsNoShow(
            String hospitalId,
            String appointmentId,
            boolean releaseCapacitySlot,
            String note // Lưu vào AppointmentStatusHistory.note
    );

    /**
     * Tra cứu chi tiết lịch khám theo mã đặt lịch (bookingCode).
     * Giải quyết: BR-CORE-03, BR-CORE-04
     */
    AppointmentDTO getAppointmentByBookingCode(
            String bookingCode
    );

    /**
     * Lấy danh sách lịch khám của một bệnh nhân tại tất cả bệnh viện.
     * Giải quyết: BR-CORE-11
     */
    List<AppointmentDTO> getPatientAppointments(
            String patientId,
            String status // Null nếu lấy tất cả (khớp AppointmentStatus)
    );

    // =========================================================================
    // RESPONSE DTOs
    // =========================================================================

    record AppointmentDTO(
            String id,
            String bookingCode,
            String hospitalId,
            String patientId,
            String serviceId,
            String windowId,
            String doctorId,
            LocalDate windowDate,
            LocalDateTime estimatedStartTime,
            LocalDateTime estimatedEndTime,
            String timeDisclaimer, // Lưu ý giờ khám thực tế có thể thay đổi (BR-CORE-03, BR-CORE-04)
            String status,         // BOOKED, CHECKED_IN, WAITING, IN_PROGRESS, COMPLETED, CANCELLED, NO_SHOW, RESCHEDULED
            String supersededById,
            String parentAppointmentId,
            String cancelledBy,
            String cancelReason,
            LocalDateTime bookedAt,
            LocalDateTime checkedInAt
    ) {}

    record CheckInResultDTO(
            String checkInId,
            String appointmentId,
            String bookingCode,
            String appointmentStatus,
            String queueEntryId,
            Integer queuePosition,
            String method,
            boolean isLate,
            LocalDateTime checkedInAt
    ) {}

    record DisruptionHandlingResultDTO(
            String hospitalId,
            String actionType,
            int totalRequested,
            int successCount,
            int failedCount,
            List<String> processedAppointmentIds,
            String reason,
            LocalDateTime processedAt
    ) {}
}